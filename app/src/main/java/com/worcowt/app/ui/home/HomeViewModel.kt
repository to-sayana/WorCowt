package com.worcowt.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worcowt.app.data.models.Routine
import com.worcowt.app.data.models.TaskLog
import com.worcowt.app.data.repository.RoutineRepository
import com.worcowt.app.data.repository.StatsRepository
import com.worcowt.app.data.repository.TaskLogRepository
import com.worcowt.app.data.repository.UserRepository
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.engine.RoutineGenerator
import com.worcowt.app.engine.XPCalculator
import com.worcowt.app.engine.StreakManager
import com.worcowt.app.utils.Constants
import com.worcowt.app.utils.toLevel
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val userRepo = UserRepository()
    private val routineRepo = RoutineRepository()
    private val taskLogRepo = TaskLogRepository()
    private val statsRepo = StatsRepository()

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _xp = MutableLiveData<Int>()
    val xp: LiveData<Int> = _xp

    private val _streak = MutableLiveData<Int>()
    val streak: LiveData<Int> = _streak

    private val _timelineItems = MutableLiveData<List<TimelineItem>>()
    val timelineItems: LiveData<List<TimelineItem>> = _timelineItems

    private val _actionResult = MutableLiveData<String?>()
    val actionResult: LiveData<String?> = _actionResult

    private var currentUserId: String? = null

    fun loadData() {
        viewModelScope.launch {
            val userId = SupabaseManager.getCurrentUserId() ?: return@launch
            currentUserId = userId

            val user = userRepo.getUser(userId) ?: return@launch
            _userName.value = user.name
            _xp.value = user.xp
            _streak.value = user.currentStreak

            var routines = routineRepo.getRoutinesForUser(userId)
            if (routines.isEmpty()) {
                val generated = RoutineGenerator.generateRoutines(user)
                routineRepo.insertRoutines(generated)
                routines = routineRepo.getRoutinesForUser(userId)
            }

            val todayLogs = taskLogRepo.getLogsForToday(userId)
            val loggedRoutineIds = todayLogs.map { it.routineId }.toSet()

            val items = routines.map { routine ->
                val log = todayLogs.find { it.routineId == routine.routineId }
                TimelineItem(
                    routine = routine,
                    status = log?.completionStatus,
                    isCompleted = log != null
                )
            }
            _timelineItems.value = items
        }
    }

    fun completeTask(routine: Routine) {
        performAction(routine, TaskLog.STATUS_COMPLETED, Constants.XP_TASK_COMPLETE)
    }

    fun snoozeTask(routine: Routine) {
        performAction(routine, TaskLog.STATUS_SNOOZED, Constants.XP_PENALTY_SNOOZE)
    }

    fun skipTask(routine: Routine) {
        performAction(routine, TaskLog.STATUS_SKIPPED, Constants.XP_PENALTY_SKIP)
    }

    private fun performAction(routine: Routine, status: String, xpDelta: Int) {
        viewModelScope.launch {
            val userId = currentUserId ?: return@launch

            val log = TaskLog(
                userId = userId,
                routineId = routine.routineId,
                completionStatus = status,
                xpAwarded = xpDelta
            )
            taskLogRepo.logTask(log)

            val user = userRepo.getUser(userId) ?: return@launch
            val newXp = (user.xp + xpDelta).coerceAtLeast(0)
            val newLevel = newXp.toLevel()
            userRepo.updateXpAndLevel(userId, newXp, newLevel, user.currentStreak)

            when (status) {
                TaskLog.STATUS_COMPLETED -> statsRepo.incrementCompleted(userId, xpDelta)
                TaskLog.STATUS_SKIPPED -> statsRepo.incrementSkipped(userId)
            }

            _xp.value = newXp

            val message = when (status) {
                TaskLog.STATUS_COMPLETED -> "Moo! +$xpDelta XP!"
                TaskLog.STATUS_SNOOZED -> "Snoozed. ${Constants.XP_PENALTY_SNOOZE} XP"
                TaskLog.STATUS_SKIPPED -> "Skipped. ${Constants.XP_PENALTY_SKIP} XP"
                else -> null
            }
            _actionResult.value = message

            loadData()
        }
    }
}

data class TimelineItem(
    val routine: Routine,
    val status: String?,
    val isCompleted: Boolean
)

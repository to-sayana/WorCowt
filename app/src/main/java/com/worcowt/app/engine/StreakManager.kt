package com.worcowt.app.engine

import com.worcowt.app.data.models.TaskLog
import java.time.LocalDate

object StreakManager {

    fun shouldContinueStreak(todayLogs: List<TaskLog>, totalRoutines: Int): Boolean {
        if (totalRoutines == 0) return false
        val completedCount = todayLogs.count { it.completionStatus == TaskLog.STATUS_COMPLETED }
        val majorityThreshold = (totalRoutines + 1) / 2
        return completedCount >= majorityThreshold
    }

    fun calculateStreak(
        currentStreak: Int,
        lastActiveDate: String?,
        todayLogs: List<TaskLog>,
        totalRoutines: Int
    ): Int {
        val today = LocalDate.now()
        val lastActive = lastActiveDate?.let {
            try { LocalDate.parse(it) } catch (e: Exception) { null }
        }

        val daysSinceActive = if (lastActive != null) {
            java.time.temporal.ChronoUnit.DAYS.between(lastActive, today).toInt()
        } else {
            Int.MAX_VALUE
        }

        return when {
            daysSinceActive > 1 -> {
                if (shouldContinueStreak(todayLogs, totalRoutines)) 1 else 0
            }
            daysSinceActive == 1 -> {
                if (shouldContinueStreak(todayLogs, totalRoutines)) {
                    currentStreak + 1
                } else {
                    0
                }
            }
            daysSinceActive == 0 -> {
                if (shouldContinueStreak(todayLogs, totalRoutines)) {
                    currentStreak.coerceAtLeast(1)
                } else {
                    currentStreak
                }
            }
            else -> currentStreak
        }
    }

    fun isStreakAtRisk(
        currentStreak: Int,
        todayLogs: List<TaskLog>,
        totalRoutines: Int
    ): Boolean {
        if (currentStreak == 0) return false
        val completedCount = todayLogs.count { it.completionStatus == TaskLog.STATUS_COMPLETED }
        val remaining = totalRoutines - todayLogs.size
        val majorityThreshold = (totalRoutines + 1) / 2
        return completedCount + remaining < majorityThreshold
    }
}

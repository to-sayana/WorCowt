package com.worcowt.app.ui.routine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worcowt.app.data.models.Routine
import com.worcowt.app.data.repository.RoutineRepository
import com.worcowt.app.data.supabase.SupabaseManager
import kotlinx.coroutines.launch

class RoutineViewModel : ViewModel() {

    private val routineRepo = RoutineRepository()

    private val _routines = MutableLiveData<List<Routine>>()
    val routines: LiveData<List<Routine>> = _routines

    fun loadRoutines() {
        viewModelScope.launch {
            val userId = SupabaseManager.getCurrentUserId() ?: return@launch
            _routines.value = routineRepo.getRoutinesForUser(userId)
        }
    }

    fun addCustomRoutine(taskName: String, scheduledTime: String) {
        viewModelScope.launch {
            val userId = SupabaseManager.getCurrentUserId() ?: return@launch
            val routine = Routine(
                userId = userId,
                taskName = taskName,
                category = Routine.CATEGORY_CUSTOM,
                scheduledTime = scheduledTime
            )
            routineRepo.insertRoutine(routine)
            loadRoutines()
        }
    }

    fun deleteRoutine(routineId: String) {
        viewModelScope.launch {
            routineRepo.deleteRoutine(routineId)
            loadRoutines()
        }
    }
}

package com.worcowt.app.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worcowt.app.data.models.User
import com.worcowt.app.data.repository.StatsRepository
import com.worcowt.app.data.repository.UserRepository
import com.worcowt.app.data.supabase.SupabaseManager
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val statsRepository = StatsRepository()

    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> = _saveResult

    fun saveOnboardingData(
        name: String,
        age: Int,
        role: String,
        wakeTime: String,
        sleepTime: String,
        mealsPerDay: Int,
        workoutPreference: Boolean,
        medicationTiming: String,
        waterGoal: Int,
        productivityPreference: String
    ) {
        viewModelScope.launch {
            val userId = SupabaseManager.getCurrentUserId()
            if (userId == null) {
                _saveResult.value = false
                return@launch
            }

            val email = SupabaseManager.auth.currentSessionOrNull()?.user?.email ?: ""

            val user = User(
                userId = userId,
                name = name,
                email = email,
                age = age,
                role = role,
                wakeTime = wakeTime,
                sleepTime = sleepTime,
                waterGoal = waterGoal,
                mealsPerDay = mealsPerDay,
                workoutPreference = workoutPreference,
                medicationTiming = medicationTiming,
                productivityPreference = productivityPreference
            )

            val userCreated = userRepository.createUser(user)
            if (userCreated) {
                statsRepository.createStats(userId)
            }
            _saveResult.value = userCreated
        }
    }
}

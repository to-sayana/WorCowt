package com.worcowt.app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worcowt.app.data.models.User
import com.worcowt.app.data.repository.UserRepository
import com.worcowt.app.data.supabase.SupabaseManager
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val userRepo = UserRepository()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    fun loadProfile() {
        viewModelScope.launch {
            val userId = SupabaseManager.getCurrentUserId() ?: return@launch
            _user.value = userRepo.getUser(userId)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            SupabaseManager.signOut()
        }
    }
}

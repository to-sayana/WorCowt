package com.worcowt.app.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worcowt.app.data.models.Statistics
import com.worcowt.app.data.repository.StatsRepository
import com.worcowt.app.data.supabase.SupabaseManager
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {

    private val statsRepo = StatsRepository()

    private val _stats = MutableLiveData<Statistics?>()
    val stats: LiveData<Statistics?> = _stats

    fun loadStats() {
        viewModelScope.launch {
            val userId = SupabaseManager.getCurrentUserId() ?: return@launch
            _stats.value = statsRepo.getStats(userId)
        }
    }
}

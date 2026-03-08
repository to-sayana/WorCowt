package com.worcowt.app.data.repository

import com.worcowt.app.data.models.Statistics
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatsRepository {

    private val table get() = SupabaseManager.db[Constants.TABLE_STATISTICS]

    suspend fun getStats(userId: String): Statistics? = withContext(Dispatchers.IO) {
        try {
            table.select {
                filter { eq("user_id", userId) }
            }.decodeSingleOrNull<Statistics>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createStats(userId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            table.insert(Statistics(userId = userId))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun updateStats(stats: Statistics): Boolean = withContext(Dispatchers.IO) {
        try {
            table.update(stats) {
                filter { eq("user_id", stats.userId) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun incrementCompleted(userId: String, xpAwarded: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val current = getStats(userId) ?: return@withContext false
                val updated = current.copy(
                    tasksCompleted = current.tasksCompleted + 1,
                    xpTotal = current.xpTotal + xpAwarded
                )
                updateStats(updated)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    suspend fun incrementSkipped(userId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val current = getStats(userId) ?: return@withContext false
            val updated = current.copy(tasksSkipped = current.tasksSkipped + 1)
            updateStats(updated)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun updateStreak(userId: String, streak: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val current = getStats(userId) ?: return@withContext false
                val longest = maxOf(current.longestStreak, streak)
                val updated = current.copy(
                    currentStreak = streak,
                    longestStreak = longest,
                    lastActiveDate = java.time.LocalDate.now().toString()
                )
                updateStats(updated)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}

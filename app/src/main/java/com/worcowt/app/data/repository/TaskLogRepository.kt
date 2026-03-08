package com.worcowt.app.data.repository

import com.worcowt.app.data.models.TaskLog
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TaskLogRepository {

    private val table get() = SupabaseManager.db[Constants.TABLE_TASK_LOGS]

    suspend fun logTask(taskLog: TaskLog): Boolean = withContext(Dispatchers.IO) {
        try {
            table.insert(taskLog)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getLogsForToday(userId: String): List<TaskLog> = withContext(Dispatchers.IO) {
        try {
            val today = LocalDate.now().toString()
            table.select {
                filter {
                    eq("user_id", userId)
                    eq("log_date", today)
                }
            }.decodeList<TaskLog>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getLogsForDate(userId: String, date: String): List<TaskLog> =
        withContext(Dispatchers.IO) {
            try {
                table.select {
                    filter {
                        eq("user_id", userId)
                        eq("log_date", date)
                    }
                }.decodeList<TaskLog>()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }

    suspend fun hasLogForRoutineToday(userId: String, routineId: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val today = LocalDate.now().toString()
                val logs = table.select {
                    filter {
                        eq("user_id", userId)
                        eq("routine_id", routineId)
                        eq("log_date", today)
                    }
                }.decodeList<TaskLog>()
                logs.isNotEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}

package com.worcowt.app.data.repository

import com.worcowt.app.data.models.Routine
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoutineRepository {

    private val table get() = SupabaseManager.db[Constants.TABLE_ROUTINES]

    suspend fun insertRoutines(routines: List<Routine>): Boolean = withContext(Dispatchers.IO) {
        try {
            table.insert(routines)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun insertRoutine(routine: Routine): Boolean = withContext(Dispatchers.IO) {
        try {
            table.insert(routine)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getRoutinesForUser(userId: String): List<Routine> = withContext(Dispatchers.IO) {
        try {
            table.select {
                filter {
                    eq("user_id", userId)
                    eq("is_active", true)
                }
                order("scheduled_time", io.github.jan.supabase.postgrest.query.Order.ASCENDING)
            }.decodeList<Routine>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun updateRoutine(routine: Routine): Boolean = withContext(Dispatchers.IO) {
        try {
            table.update(routine) {
                filter { eq("routine_id", routine.routineId) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteRoutine(routineId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            table.delete {
                filter { eq("routine_id", routineId) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deactivateRoutine(routineId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            table.update({
                set("is_active", false)
            }) {
                filter { eq("routine_id", routineId) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

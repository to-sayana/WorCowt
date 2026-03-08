package com.worcowt.app.data.repository

import com.worcowt.app.data.models.User
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.utils.Constants
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val table get() = SupabaseManager.db[Constants.TABLE_USERS]

    suspend fun createUser(user: User): Boolean = withContext(Dispatchers.IO) {
        try {
            table.insert(user)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
        try {
            table.select {
                filter { eq("user_id", userId) }
            }.decodeSingleOrNull<User>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateUser(user: User): Boolean = withContext(Dispatchers.IO) {
        try {
            table.update(user) {
                filter { eq("user_id", user.userId) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun updateXpAndLevel(userId: String, xp: Int, level: Int, streak: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                table.update({
                    set("xp", xp)
                    set("level", level)
                    set("current_streak", streak)
                }) {
                    filter { eq("user_id", userId) }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
}

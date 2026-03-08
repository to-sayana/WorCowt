package com.worcowt.app.data.supabase

import com.worcowt.app.utils.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SupabaseManager {

    lateinit var client: SupabaseClient
        private set

    fun init() {
        client = createSupabaseClient(
            supabaseUrl = Constants.SUPABASE_URL,
            supabaseKey = Constants.SUPABASE_ANON_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
        }
    }

    val auth get() = client.auth
    val db get() = client.postgrest

    suspend fun signUp(email: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                val userId = auth.currentSessionOrNull()?.user?.id
                if (userId != null) {
                    Result.success(userId)
                } else {
                    Result.success("check_email")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

    suspend fun signIn(email: String, password: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                Result.success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

    suspend fun signOut() {
        withContext(Dispatchers.IO) {
            try {
                auth.signOut()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return try {
            auth.currentSessionOrNull() != null
        } catch (e: Exception) {
            false
        }
    }

    fun getCurrentUserId(): String? {
        return try {
            auth.currentSessionOrNull()?.user?.id
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentUserEmail(): String? {
        return try {
            auth.currentSessionOrNull()?.user?.email
        } catch (e: Exception) {
            null
        }
    }
}

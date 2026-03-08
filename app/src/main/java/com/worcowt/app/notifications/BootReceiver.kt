package com.worcowt.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.worcowt.app.data.repository.RoutineRepository
import com.worcowt.app.data.supabase.SupabaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userId = SupabaseManager.getCurrentUserId() ?: return@launch
                val routineRepo = RoutineRepository()
                val routines = routineRepo.getRoutinesForUser(userId)
                NotificationHelper.scheduleRoutineReminders(context, routines)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }
}

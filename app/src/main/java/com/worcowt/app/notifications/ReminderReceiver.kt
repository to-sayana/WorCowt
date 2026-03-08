package com.worcowt.app.notifications

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.worcowt.app.R
import com.worcowt.app.utils.Constants

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_TASK_NAME = "extra_task_name"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_ROUTINE_ID = "extra_routine_id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra(EXTRA_TASK_NAME) ?: "Task Reminder"
        val category = intent.getStringExtra(EXTRA_CATEGORY) ?: ""

        val motivationalMessage = NotificationHelper.getMotivationalMessage(category)

        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(taskName)
            .setContentText(motivationalMessage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 300, 200, 300))
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = taskName.hashCode()
        notificationManager.notify(notificationId, notification)
    }
}

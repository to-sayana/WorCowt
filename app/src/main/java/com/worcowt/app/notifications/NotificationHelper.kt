package com.worcowt.app.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.worcowt.app.data.models.Routine
import com.worcowt.app.utils.Constants
import java.util.Calendar

object NotificationHelper {

    fun scheduleRoutineReminders(context: Context, routines: List<Routine>) {
        cancelAllReminders(context, routines)
        routines.forEachIndexed { index, routine ->
            scheduleReminder(context, routine, Constants.NOTIFICATION_REQUEST_BASE + index)
        }
    }

    fun scheduleReminder(context: Context, routine: Routine, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_TASK_NAME, routine.taskName)
            putExtra(ReminderReceiver.EXTRA_CATEGORY, routine.category)
            putExtra(ReminderReceiver.EXTRA_ROUTINE_ID, routine.routineId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = parseScheduledTime(routine.scheduledTime) ?: return

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelAllReminders(context: Context, routines: List<Routine>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        routines.forEachIndexed { index, _ ->
            val intent = Intent(context, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                Constants.NOTIFICATION_REQUEST_BASE + index,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            pendingIntent?.let { alarmManager.cancel(it) }
        }
    }

    private fun parseScheduledTime(time: String): Calendar? {
        return try {
            val parts = time.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getMotivationalMessage(category: String): String {
        return when (category) {
            Routine.CATEGORY_WATER -> "Moo! Stay hydrated! Time for a glass of water!"
            Routine.CATEGORY_WORKOUT -> "Moo! Time to get moving! Let's work out!"
            Routine.CATEGORY_SLEEP -> "Moo! Time to wind down and rest up!"
            Routine.CATEGORY_MEDICATION -> "Moo! Don't forget your medication!"
            Routine.CATEGORY_PRODUCTIVITY -> "Moo! Focus time! Let's be productive!"
            Routine.CATEGORY_MEAL -> "Moo! Time for a healthy meal!"
            else -> "Moo! Keep going, you're doing great!"
        }
    }
}

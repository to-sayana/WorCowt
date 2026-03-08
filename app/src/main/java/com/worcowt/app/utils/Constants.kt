package com.worcowt.app.utils

object Constants {
    const val SUPABASE_URL = "https://vpiwqfpcgdlqdgrkxkfy.supabase.co"
    const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZwaXdxZnBjZ2RscWRncmt4a2Z5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzI5NjUxMTcsImV4cCI6MjA4ODU0MTExN30.9bQElw5gspziauZaewX59beLwyzTykUXGqUrAxvXG_0"

    const val NOTIFICATION_CHANNEL_ID = "worcowt_reminders"
    const val NOTIFICATION_REQUEST_BASE = 1000

    const val PREF_NAME = "worcowt_prefs"
    const val PREF_ONBOARDING_COMPLETE = "onboarding_complete"
    const val PREF_USER_ID = "user_id"

    const val XP_TASK_COMPLETE = 10
    const val XP_DAILY_COMPLETE = 50
    const val XP_WEEKLY_STREAK = 100
    const val XP_PENALTY_SNOOZE = -5
    const val XP_PENALTY_SKIP = -3

    val LEVEL_THRESHOLDS = mapOf(
        1 to 0,
        2 to 200,
        3 to 500,
        4 to 900,
        5 to 1500
    )

    const val TABLE_USERS = "users"
    const val TABLE_ROUTINES = "routines"
    const val TABLE_TASK_LOGS = "task_logs"
    const val TABLE_STATISTICS = "statistics"
}

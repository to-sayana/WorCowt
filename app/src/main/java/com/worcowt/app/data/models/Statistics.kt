package com.worcowt.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Statistics(
    @SerialName("user_id") val userId: String = "",
    @SerialName("tasks_completed") val tasksCompleted: Int = 0,
    @SerialName("tasks_skipped") val tasksSkipped: Int = 0,
    @SerialName("tasks_snoozed") val tasksSnoozed: Int = 0,
    @SerialName("current_streak") val currentStreak: Int = 0,
    @SerialName("longest_streak") val longestStreak: Int = 0,
    @SerialName("xp_total") val xpTotal: Int = 0,
    @SerialName("last_active_date") val lastActiveDate: String? = null
)

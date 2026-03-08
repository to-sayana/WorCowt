package com.worcowt.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("user_id") val userId: String = "",
    @SerialName("name") val name: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("age") val age: Int = 0,
    @SerialName("role") val role: String = "student",
    @SerialName("wake_time") val wakeTime: String = "07:00",
    @SerialName("sleep_time") val sleepTime: String = "23:00",
    @SerialName("water_goal") val waterGoal: Int = 8,
    @SerialName("meals_per_day") val mealsPerDay: Int = 3,
    @SerialName("workout_preference") val workoutPreference: Boolean = false,
    @SerialName("medication_timing") val medicationTiming: String = "",
    @SerialName("productivity_preference") val productivityPreference: String = "morning",
    @SerialName("xp") val xp: Int = 0,
    @SerialName("level") val level: Int = 1,
    @SerialName("current_streak") val currentStreak: Int = 0,
    @SerialName("created_at") val createdAt: String? = null
)

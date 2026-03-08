package com.worcowt.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Routine(
    @SerialName("routine_id") val routineId: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("task_name") val taskName: String = "",
    @SerialName("category") val category: String = "",
    @SerialName("scheduled_time") val scheduledTime: String = "",
    @SerialName("recurring") val recurring: Boolean = true,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("created_at") val createdAt: String? = null
) {
    companion object {
        const val CATEGORY_SLEEP = "sleep"
        const val CATEGORY_WATER = "water"
        const val CATEGORY_MEAL = "meal"
        const val CATEGORY_MEDICATION = "medication"
        const val CATEGORY_WORKOUT = "workout"
        const val CATEGORY_PRODUCTIVITY = "productivity"
        const val CATEGORY_CUSTOM = "custom"
    }
}

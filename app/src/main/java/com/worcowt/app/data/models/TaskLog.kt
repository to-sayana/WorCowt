package com.worcowt.app.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskLog(
    @SerialName("log_id") val logId: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("routine_id") val routineId: String = "",
    @SerialName("completion_status") val completionStatus: String = "",
    @SerialName("completion_time") val completionTime: String? = null,
    @SerialName("xp_awarded") val xpAwarded: Int = 0,
    @SerialName("log_date") val logDate: String? = null
) {
    companion object {
        const val STATUS_COMPLETED = "completed"
        const val STATUS_SNOOZED = "snoozed"
        const val STATUS_SKIPPED = "skipped"
    }
}

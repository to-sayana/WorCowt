package com.worcowt.app.engine

import com.worcowt.app.data.models.TaskLog
import com.worcowt.app.utils.Constants

object XPCalculator {

    fun calculateXpForAction(status: String): Int {
        return when (status) {
            TaskLog.STATUS_COMPLETED -> Constants.XP_TASK_COMPLETE
            TaskLog.STATUS_SNOOZED -> Constants.XP_PENALTY_SNOOZE
            TaskLog.STATUS_SKIPPED -> Constants.XP_PENALTY_SKIP
            else -> 0
        }
    }

    fun calculateDailyBonus(todayLogs: List<TaskLog>, totalRoutines: Int): Int {
        val completedCount = todayLogs.count { it.completionStatus == TaskLog.STATUS_COMPLETED }
        return if (completedCount >= totalRoutines && totalRoutines > 0) {
            Constants.XP_DAILY_COMPLETE
        } else {
            0
        }
    }

    fun calculateWeeklyStreakBonus(currentStreak: Int): Int {
        return if (currentStreak > 0 && currentStreak % 7 == 0) {
            Constants.XP_WEEKLY_STREAK
        } else {
            0
        }
    }

    fun calculateLevel(totalXp: Int): Int {
        val sorted = Constants.LEVEL_THRESHOLDS.entries.sortedByDescending { it.value }
        for ((level, threshold) in sorted) {
            if (totalXp >= threshold) return level
        }
        return 1
    }

    fun xpForNextLevel(currentXp: Int): Int {
        val currentLevel = calculateLevel(currentXp)
        val nextLevel = currentLevel + 1
        return Constants.LEVEL_THRESHOLDS[nextLevel] ?: Int.MAX_VALUE
    }

    fun xpProgressPercent(currentXp: Int): Float {
        val currentLevel = calculateLevel(currentXp)
        val currentThreshold = Constants.LEVEL_THRESHOLDS[currentLevel] ?: 0
        val nextThreshold = Constants.LEVEL_THRESHOLDS[currentLevel + 1]
            ?: return 100f

        val progressInLevel = currentXp - currentThreshold
        val levelRange = nextThreshold - currentThreshold
        return if (levelRange > 0) {
            (progressInLevel.toFloat() / levelRange * 100f).coerceIn(0f, 100f)
        } else {
            100f
        }
    }

    fun calculateEndOfDayXp(
        currentXp: Int,
        todayLogs: List<TaskLog>,
        totalRoutines: Int,
        currentStreak: Int
    ): Int {
        var totalEarned = 0
        todayLogs.forEach { log ->
            totalEarned += calculateXpForAction(log.completionStatus)
        }
        totalEarned += calculateDailyBonus(todayLogs, totalRoutines)
        totalEarned += calculateWeeklyStreakBonus(currentStreak)
        return (currentXp + totalEarned).coerceAtLeast(0)
    }
}

package com.worcowt.app.engine

import com.worcowt.app.data.models.Routine
import com.worcowt.app.data.models.User

object RoutineGenerator {

    fun generateRoutines(user: User): List<Routine> {
        val routines = mutableListOf<Routine>()
        val wakeHour = parseHour(user.wakeTime)
        val sleepHour = parseHour(user.sleepTime)

        routines.add(makeRoutine(user.userId, "Wake Up", Routine.CATEGORY_SLEEP, user.wakeTime))

        routines.add(makeRoutine(
            user.userId, "Drink Water",
            Routine.CATEGORY_WATER,
            formatTime(wakeHour, 15)
        ))

        val waterInterval = calculateWaterInterval(wakeHour, sleepHour, user.waterGoal)
        for (i in 1 until user.waterGoal) {
            val hour = wakeHour + (waterInterval * i) / 60
            val minute = (waterInterval * i) % 60
            if (hour < sleepHour) {
                routines.add(makeRoutine(
                    user.userId, "Drink Water",
                    Routine.CATEGORY_WATER,
                    formatTime(hour, minute)
                ))
            }
        }

        val breakfastHour = wakeHour + 1
        routines.add(makeRoutine(
            user.userId, "Breakfast",
            Routine.CATEGORY_MEAL,
            formatTime(breakfastHour, 0)
        ))

        if (user.mealsPerDay >= 2) {
            val lunchHour = (wakeHour + sleepHour) / 2
            routines.add(makeRoutine(
                user.userId, "Lunch",
                Routine.CATEGORY_MEAL,
                formatTime(lunchHour, 0)
            ))
        }

        if (user.mealsPerDay >= 3) {
            val dinnerHour = sleepHour - 2
            routines.add(makeRoutine(
                user.userId, "Dinner",
                Routine.CATEGORY_MEAL,
                formatTime(dinnerHour, 0)
            ))
        }

        if (user.workoutPreference) {
            val workoutHour = if (user.productivityPreference == "morning")
                wakeHour + 2 else sleepHour - 4
            routines.add(makeRoutine(
                user.userId, "Workout",
                Routine.CATEGORY_WORKOUT,
                formatTime(workoutHour, 0)
            ))
        }

        if (user.medicationTiming.isNotEmpty() && user.medicationTiming != "No") {
            when {
                user.medicationTiming.contains("morning", true) -> {
                    routines.add(makeRoutine(
                        user.userId, "Take Medication (Morning)",
                        Routine.CATEGORY_MEDICATION,
                        formatTime(wakeHour, 30)
                    ))
                }
                user.medicationTiming.contains("evening", true) -> {
                    routines.add(makeRoutine(
                        user.userId, "Take Medication (Evening)",
                        Routine.CATEGORY_MEDICATION,
                        formatTime(sleepHour - 1, 0)
                    ))
                }
                user.medicationTiming.contains("both", true) -> {
                    routines.add(makeRoutine(
                        user.userId, "Take Medication (Morning)",
                        Routine.CATEGORY_MEDICATION,
                        formatTime(wakeHour, 30)
                    ))
                    routines.add(makeRoutine(
                        user.userId, "Take Medication (Evening)",
                        Routine.CATEGORY_MEDICATION,
                        formatTime(sleepHour - 1, 0)
                    ))
                }
            }
        }

        val prodStart = if (user.productivityPreference == "morning")
            wakeHour + 2 else (wakeHour + sleepHour) / 2 + 1
        routines.add(makeRoutine(
            user.userId, "Focus Time - Study/Work",
            Routine.CATEGORY_PRODUCTIVITY,
            formatTime(prodStart, 0)
        ))
        routines.add(makeRoutine(
            user.userId, "Focus Time - Session 2",
            Routine.CATEGORY_PRODUCTIVITY,
            formatTime(prodStart + 3, 0)
        ))

        routines.add(makeRoutine(
            user.userId, "Wind Down",
            Routine.CATEGORY_SLEEP,
            formatTime(sleepHour - 1, 0)
        ))
        routines.add(makeRoutine(
            user.userId, "Sleep",
            Routine.CATEGORY_SLEEP,
            user.sleepTime
        ))

        return routines.sortedBy { it.scheduledTime }
    }

    private fun makeRoutine(
        userId: String,
        taskName: String,
        category: String,
        scheduledTime: String
    ): Routine {
        return Routine(
            userId = userId,
            taskName = taskName,
            category = category,
            scheduledTime = scheduledTime
        )
    }

    private fun parseHour(time: String): Int {
        return try {
            time.split(":")[0].toInt()
        } catch (e: Exception) {
            7
        }
    }

    private fun formatTime(hour: Int, minute: Int): String {
        val h = hour.coerceIn(0, 23)
        val m = minute.coerceIn(0, 59)
        return String.format("%02d:%02d", h, m)
    }

    private fun calculateWaterInterval(wakeHour: Int, sleepHour: Int, waterGoal: Int): Int {
        val awakeMinutes = (sleepHour - wakeHour).coerceAtLeast(1) * 60
        return awakeMinutes / waterGoal.coerceAtLeast(1)
    }
}

package com.worcowt.app.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.getAppPrefs(): SharedPreferences =
    getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

fun View.visible() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun Calendar.formatTime(pattern: String = "hh:mm a"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(time)
}

fun String.parseTime(pattern: String = "HH:mm"): Calendar? {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val date = sdf.parse(this) ?: return null
        Calendar.getInstance().apply {
            time = date
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    } catch (e: Exception) {
        null
    }
}

fun Int.toLevel(): Int {
    val sorted = Constants.LEVEL_THRESHOLDS.entries.sortedByDescending { it.value }
    for ((level, threshold) in sorted) {
        if (this >= threshold) return level
    }
    return 1
}

fun Int.xpToNextLevel(): Int {
    val currentLevel = this.toLevel()
    val nextLevel = currentLevel + 1
    return Constants.LEVEL_THRESHOLDS[nextLevel] ?: Constants.LEVEL_THRESHOLDS.values.max()
}

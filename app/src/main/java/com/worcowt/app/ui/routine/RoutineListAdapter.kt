package com.worcowt.app.ui.routine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worcowt.app.R
import com.worcowt.app.data.models.Routine

class RoutineListAdapter(
    private val onDelete: (Routine) -> Unit
) : ListAdapter<Routine, RoutineListAdapter.RoutineViewHolder>(DiffCallback()) {

    inner class RoutineViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTaskName: TextView = view.findViewById(R.id.tvRoutineName)
        val tvTime: TextView = view.findViewById(R.id.tvRoutineTime)
        val tvCategory: TextView = view.findViewById(R.id.tvRoutineCategory)
        val ivIcon: ImageView = view.findViewById(R.id.ivRoutineIcon)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDeleteRoutine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_routine, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = getItem(position)
        holder.tvTaskName.text = routine.taskName
        holder.tvTime.text = formatDisplayTime(routine.scheduledTime)
        holder.tvCategory.text = routine.category.replaceFirstChar { it.uppercase() }

        val iconRes = when (routine.category) {
            Routine.CATEGORY_WATER -> R.drawable.mascot_water
            Routine.CATEGORY_WORKOUT -> R.drawable.mascot_workout
            Routine.CATEGORY_SLEEP -> R.drawable.mascot_sleep
            Routine.CATEGORY_MEDICATION -> R.drawable.mascot_medicine
            Routine.CATEGORY_PRODUCTIVITY -> R.drawable.mascot_productivity
            else -> R.drawable.mascot_landing
        }
        holder.ivIcon.setImageResource(iconRes)
        holder.btnDelete.setOnClickListener { onDelete(routine) }
    }

    private fun formatDisplayTime(time: String): String {
        return try {
            val parts = time.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()
            val amPm = if (hour < 12) "AM" else "PM"
            val displayHour = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }
            String.format("%d:%02d %s", displayHour, minute, amPm)
        } catch (e: Exception) {
            time
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Routine>() {
        override fun areItemsTheSame(old: Routine, new: Routine) =
            old.routineId == new.routineId
        override fun areContentsTheSame(old: Routine, new: Routine) = old == new
    }
}

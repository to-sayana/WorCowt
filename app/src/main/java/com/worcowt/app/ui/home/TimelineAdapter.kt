package com.worcowt.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.worcowt.app.R
import com.worcowt.app.data.models.Routine
import com.worcowt.app.data.models.TaskLog

class TimelineAdapter(
    private val onComplete: (Routine) -> Unit,
    private val onSnooze: (Routine) -> Unit,
    private val onSkip: (Routine) -> Unit
) : ListAdapter<TimelineItem, TimelineAdapter.TimelineViewHolder>(DiffCallback()) {

    inner class TimelineViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTaskName: TextView = view.findViewById(R.id.tvTaskName)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val ivCategoryIcon: ImageView = view.findViewById(R.id.ivCategoryIcon)
        val ivStatusDot: View = view.findViewById(R.id.statusDot)
        val timelineLine: View = view.findViewById(R.id.timelineLine)
        val btnComplete: MaterialButton = view.findViewById(R.id.btnComplete)
        val btnSnooze: MaterialButton = view.findViewById(R.id.btnSnooze)
        val btnSkip: MaterialButton = view.findViewById(R.id.btnSkip)
        val actionsRow: View = view.findViewById(R.id.actionsRow)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timeline_task, parent, false)
        return TimelineViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val item = getItem(position)
        val context = holder.view.context

        holder.tvTime.text = formatDisplayTime(item.routine.scheduledTime)
        holder.tvTaskName.text = item.routine.taskName
        holder.tvCategory.text = item.routine.category.replaceFirstChar { it.uppercase() }

        val iconRes = getCategoryIcon(item.routine.category)
        holder.ivCategoryIcon.setImageResource(iconRes)

        holder.timelineLine.visibility = if (position < itemCount - 1) View.VISIBLE else View.INVISIBLE

        if (item.isCompleted) {
            holder.actionsRow.visibility = View.GONE
            holder.tvStatus.visibility = View.VISIBLE
            when (item.status) {
                TaskLog.STATUS_COMPLETED -> {
                    holder.tvStatus.text = "Completed"
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_complete))
                    holder.ivStatusDot.setBackgroundColor(ContextCompat.getColor(context, R.color.status_complete))
                }
                TaskLog.STATUS_SNOOZED -> {
                    holder.tvStatus.text = "Snoozed"
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_snoozed))
                    holder.ivStatusDot.setBackgroundColor(ContextCompat.getColor(context, R.color.status_snoozed))
                }
                TaskLog.STATUS_SKIPPED -> {
                    holder.tvStatus.text = "Skipped"
                    holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_skipped))
                    holder.ivStatusDot.setBackgroundColor(ContextCompat.getColor(context, R.color.status_skipped))
                }
            }
        } else {
            holder.actionsRow.visibility = View.VISIBLE
            holder.tvStatus.visibility = View.GONE
            holder.ivStatusDot.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary))

            holder.btnComplete.setOnClickListener { onComplete(item.routine) }
            holder.btnSnooze.setOnClickListener { onSnooze(item.routine) }
            holder.btnSkip.setOnClickListener { onSkip(item.routine) }
        }
    }

    private fun getCategoryIcon(category: String): Int = when (category) {
        Routine.CATEGORY_WATER -> R.drawable.mascot_water
        Routine.CATEGORY_WORKOUT -> R.drawable.mascot_workout
        Routine.CATEGORY_SLEEP -> R.drawable.mascot_sleep
        Routine.CATEGORY_MEDICATION -> R.drawable.mascot_medicine
        Routine.CATEGORY_PRODUCTIVITY -> R.drawable.mascot_productivity
        else -> R.drawable.mascot_landing
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

    class DiffCallback : DiffUtil.ItemCallback<TimelineItem>() {
        override fun areItemsTheSame(old: TimelineItem, new: TimelineItem) =
            old.routine.routineId == new.routine.routineId
        override fun areContentsTheSame(old: TimelineItem, new: TimelineItem) = old == new
    }
}

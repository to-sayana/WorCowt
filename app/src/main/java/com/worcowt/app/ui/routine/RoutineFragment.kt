package com.worcowt.app.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.worcowt.app.R
import com.worcowt.app.databinding.FragmentRoutineBinding
import com.worcowt.app.utils.showToast

class RoutineFragment : Fragment() {

    private var _binding: FragmentRoutineBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RoutineViewModel
    private lateinit var adapter: RoutineListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RoutineViewModel::class.java]

        adapter = RoutineListAdapter(
            onDelete = { routine -> viewModel.deleteRoutine(routine.routineId) }
        )

        binding.rvRoutines.layoutManager = LinearLayoutManager(context)
        binding.rvRoutines.adapter = adapter

        binding.fabAddRoutine.setOnClickListener { showAddRoutineDialog() }

        viewModel.routines.observe(viewLifecycleOwner) { routines ->
            adapter.submitList(routines)
        }

        viewModel.loadRoutines()
    }

    private fun showAddRoutineDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_routine, null)

        val etTaskName = dialogView.findViewById<TextInputEditText>(R.id.etTaskName)
        var selectedTime = "08:00"
        val tvTime = dialogView.findViewById<android.widget.TextView>(R.id.tvSelectedTime)

        dialogView.findViewById<View>(R.id.btnPickTime).setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(8)
                .setMinute(0)
                .setTitleText("Select time")
                .build()
            picker.addOnPositiveButtonClickListener {
                selectedTime = String.format("%02d:%02d", picker.hour, picker.minute)
                tvTime.text = selectedTime
            }
            picker.show(childFragmentManager, "timePicker")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Custom Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etTaskName?.text?.toString() ?: ""
                if (name.isNotBlank()) {
                    viewModel.addCustomRoutine(name, selectedTime)
                } else {
                    requireContext().showToast("Please enter a task name")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

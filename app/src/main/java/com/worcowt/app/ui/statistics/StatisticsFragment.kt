package com.worcowt.app.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.worcowt.app.R
import com.worcowt.app.databinding.FragmentStatisticsBinding
import com.worcowt.app.engine.XPCalculator

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]

        observeViewModel()
        viewModel.loadStats()
    }

    private fun observeViewModel() {
        viewModel.stats.observe(viewLifecycleOwner) { stats ->
            if (stats != null) {
                binding.tvXpTotal.text = stats.xpTotal.toString()
                binding.tvCurrentStreak.text = "${stats.currentStreak} days"
                binding.tvLongestStreak.text = "${stats.longestStreak} days"
                binding.tvTasksCompleted.text = stats.tasksCompleted.toString()
                binding.tvTasksSkipped.text = stats.tasksSkipped.toString()

                val level = XPCalculator.calculateLevel(stats.xpTotal)
                binding.tvLevel.text = getString(R.string.level_label, level)

                val progress = XPCalculator.xpProgressPercent(stats.xpTotal)
                binding.progressXp.progress = progress.toInt()

                val nextLevelXp = XPCalculator.xpForNextLevel(stats.xpTotal)
                binding.tvNextLevel.text = if (nextLevelXp < Int.MAX_VALUE) {
                    "${stats.xpTotal} / $nextLevelXp XP"
                } else {
                    "MAX LEVEL"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

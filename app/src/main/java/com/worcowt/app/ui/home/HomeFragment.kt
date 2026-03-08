package com.worcowt.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.worcowt.app.R
import com.worcowt.app.databinding.FragmentHomeBinding
import com.worcowt.app.utils.showToast
import com.worcowt.app.utils.toLevel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var timelineAdapter: TimelineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        viewModel.loadData()
    }

    private fun setupRecyclerView() {
        timelineAdapter = TimelineAdapter(
            onComplete = { routine -> viewModel.completeTask(routine) },
            onSnooze = { routine -> viewModel.snoozeTask(routine) },
            onSkip = { routine -> viewModel.skipTask(routine) }
        )
        binding.rvTimeline.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timelineAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvGreeting.text = if (name.isNotEmpty())
                "Hey $name! ${getString(R.string.moo_greeting)}" else getString(R.string.good_morning)
        }

        viewModel.xp.observe(viewLifecycleOwner) { xp ->
            binding.tvXp.text = "$xp XP"
            binding.tvLevel.text = getString(R.string.level_label, xp.toLevel())
        }

        viewModel.streak.observe(viewLifecycleOwner) { streak ->
            binding.tvStreak.text = "$streak"
        }

        viewModel.timelineItems.observe(viewLifecycleOwner) { items ->
            timelineAdapter.submitList(items)
            binding.tvEmptyState.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.actionResult.observe(viewLifecycleOwner) { message ->
            message?.let { requireContext().showToast(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.worcowt.app.ui.workout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.worcowt.app.databinding.ActivityWorkoutBinding

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding

    private val workoutTips = listOf(
        "Moo! Start with 10 minutes of stretching to warm up!",
        "Moo! Try 20 push-ups today - you've got this!",
        "Moo! A 15-minute walk does wonders for your mood!",
        "Moo! Don't forget to hydrate during your workout!",
        "Moo! Rest days are important too - listen to your body!",
        "Moo! Try a new exercise today to keep things fun!",
        "Moo! Consistency beats intensity - keep showing up!"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.tvMotivation.text = workoutTips.random()

        binding.btnNewTip.setOnClickListener {
            binding.tvMotivation.text = workoutTips.random()
        }
    }
}

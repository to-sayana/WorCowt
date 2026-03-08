package com.worcowt.app.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.worcowt.app.R
import com.worcowt.app.databinding.ActivityProfileBinding
import com.worcowt.app.ui.landing.LandingActivity
import com.worcowt.app.utils.Constants
import com.worcowt.app.utils.getAppPrefs
import com.worcowt.app.utils.toLevel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
            getAppPrefs().edit().clear().apply()
            val intent = Intent(this, LandingActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                binding.tvName.text = user.name.ifEmpty { "User" }
                binding.tvEmail.text = user.email
                binding.tvRole.text = user.role.replaceFirstChar { it.uppercase() }
                binding.tvXp.text = "${user.xp} XP"
                binding.tvLevel.text = getString(R.string.level_label, user.xp.toLevel())
                binding.tvStreak.text = "${user.currentStreak} day streak"
                binding.tvWakeTime.text = "Wake: ${user.wakeTime}"
                binding.tvSleepTime.text = "Sleep: ${user.sleepTime}"
            }
        }

        viewModel.loadProfile()
    }
}

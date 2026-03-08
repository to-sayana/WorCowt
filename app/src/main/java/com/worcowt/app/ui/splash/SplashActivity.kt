package com.worcowt.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.ui.landing.LandingActivity
import com.worcowt.app.ui.main.MainActivity
import com.worcowt.app.ui.onboarding.OnboardingActivity
import com.worcowt.app.utils.Constants
import com.worcowt.app.utils.getAppPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(1200)
            navigate()
        }
    }

    private fun navigate() {
        val destination = when {
            !SupabaseManager.isLoggedIn() -> LandingActivity::class.java
            !getAppPrefs().getBoolean(Constants.PREF_ONBOARDING_COMPLETE, false) ->
                OnboardingActivity::class.java
            else -> MainActivity::class.java
        }
        startActivity(Intent(this, destination))
        finish()
    }
}

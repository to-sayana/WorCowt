package com.worcowt.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.worcowt.app.R
import com.worcowt.app.data.supabase.SupabaseManager
import com.worcowt.app.databinding.ActivityLoginBinding
import com.worcowt.app.ui.onboarding.OnboardingActivity
import com.worcowt.app.ui.main.MainActivity
import com.worcowt.app.utils.Constants
import com.worcowt.app.utils.getAppPrefs
import com.worcowt.app.utils.showToast
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isSignUpMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill in all fields")
                return@setOnClickListener
            }
            if (password.length < 6) {
                showToast("Password must be at least 6 characters")
                return@setOnClickListener
            }

            if (isSignUpMode) {
                val confirmPassword = binding.etConfirmPassword.text.toString().trim()
                if (password != confirmPassword) {
                    showToast("Passwords don't match")
                    return@setOnClickListener
                }
                performSignUp(email, password)
            } else {
                performSignIn(email, password)
            }
        }

        binding.tvToggleMode.setOnClickListener {
            isSignUpMode = !isSignUpMode
            updateUI()
        }
    }

    private fun updateUI() {
        if (isSignUpMode) {
            binding.tvFormTitle.text = "Create Account"
            binding.btnSubmit.text = "Sign Up"
            binding.tvToggleMode.text = "Already have an account? Sign In"
            binding.confirmPasswordLayout.visibility = View.VISIBLE
        } else {
            binding.tvFormTitle.text = "Sign In"
            binding.btnSubmit.text = "Sign In"
            binding.tvToggleMode.text = "Don't have an account? Sign Up"
            binding.confirmPasswordLayout.visibility = View.GONE
        }
    }

    private fun performSignUp(email: String, password: String) {
        setLoading(true)
        lifecycleScope.launch {
            val result = SupabaseManager.signUp(email, password)
            setLoading(false)
            result.onSuccess {
                showToast("Account created! Signing in...")
                val signInResult = SupabaseManager.signIn(email, password)
                signInResult.onSuccess {
                    navigateToOnboarding()
                }.onFailure { e ->
                    showToast("Account created. Sign in failed: ${e.message}")
                    isSignUpMode = false
                    updateUI()
                }
            }.onFailure { e ->
                showToast(e.message ?: getString(R.string.login_error))
            }
        }
    }

    private fun performSignIn(email: String, password: String) {
        setLoading(true)
        lifecycleScope.launch {
            val result = SupabaseManager.signIn(email, password)
            setLoading(false)
            result.onSuccess {
                val onboardingDone = getAppPrefs().getBoolean(Constants.PREF_ONBOARDING_COMPLETE, false)
                if (onboardingDone) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    navigateToOnboarding()
                }
                finish()
            }.onFailure { e ->
                val msg = e.message ?: getString(R.string.login_error)
                showToast(msg, android.widget.Toast.LENGTH_LONG)
            }
        }
    }

    private fun navigateToOnboarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnSubmit.isEnabled = !loading
        binding.etEmail.isEnabled = !loading
        binding.etPassword.isEnabled = !loading
        binding.etConfirmPassword.isEnabled = !loading
    }
}

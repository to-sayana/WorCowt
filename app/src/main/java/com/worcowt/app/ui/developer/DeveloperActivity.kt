package com.worcowt.app.ui.developer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.worcowt.app.databinding.ActivityDeveloperBinding

class DeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}

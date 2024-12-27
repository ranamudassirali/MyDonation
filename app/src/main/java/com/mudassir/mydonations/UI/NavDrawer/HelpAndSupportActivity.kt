package com.mudassir.mydonations.UI.NavDrawer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mudassir.mydonations.R
import com.mudassir.mydonations.databinding.ActivityHelpAndSupportBinding
import dagger.hilt.android.AndroidEntryPoint

class HelpAndSupportActivity : AppCompatActivity() {
    lateinit var binding: ActivityHelpAndSupportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityHelpAndSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
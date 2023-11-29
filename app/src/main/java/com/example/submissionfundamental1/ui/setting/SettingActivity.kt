package com.example.submissionfundamental1.ui.setting

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.submissionfundamental1.R
import com.example.submissionfundamental1.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreference(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.setTheme().observe(this) { isDarkTheme ->
            val themeTextResId = if (isDarkTheme) R.string.dark_theme else R.string.light_theme
            binding.switchTheme.text = getString(themeTextResId)
            val nightMode =
                if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(nightMode)
            binding.switchTheme.isChecked = isDarkTheme
        }
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
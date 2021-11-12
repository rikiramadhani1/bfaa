package com.riki.githubuser.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.riki.githubuser.R
import com.riki.githubuser.databinding.ActivitySettingBinding
import com.riki.githubuser.utils.SettingPreferences
import com.riki.githubuser.viewmodel.SettingViewModel
import com.riki.githubuser.viewmodel.ViewModelFactorySetting

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
    private lateinit var settingBinding: ActivitySettingBinding
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        setupToolbar()
        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel =
            ViewModelProvider(this, ViewModelFactorySetting(pref)).get(SettingViewModel::class.java)
        darkMode()
    }

    private fun darkMode() {
        var isDarkMode = false

        settingViewModel.getThemeSettings().observe(this, { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                settingBinding.tvDarkmode.text = resources.getText(R.string.on_light_mode)
                settingBinding.ivDarkmode.setImageResource(R.drawable.ic_moon)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                settingBinding.tvDarkmode.text = resources.getText(R.string.on_dark_mode)
                settingBinding.ivDarkmode.setImageResource(R.drawable.ic_sun)
            }
        })

        settingBinding.ivDarkmode.setOnClickListener {
            isDarkMode = !isDarkMode
            settingViewModel.saveThemeSetting(isDarkMode)
        }
    }

    private fun setupToolbar() {
        settingBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(settingBinding.toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.setting)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
package com.allana.bfaa.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allana.bfaa.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        initSettingFragment()
    }

    private fun initSettingFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }

}
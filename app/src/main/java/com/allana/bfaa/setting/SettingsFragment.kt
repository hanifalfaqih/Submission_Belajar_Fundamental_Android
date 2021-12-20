package com.allana.bfaa.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.allana.bfaa.R
import com.allana.bfaa.alarm.AlarmService

/**
 * replace with setting preference fragment
 */
class SettingsFragment(): PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var alarmReminderPreference: SwitchPreferenceCompat
    private lateinit var REMINDER: String
    private lateinit var alarmService: AlarmService

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        alarmService = AlarmService()

        initPreference()
        setSummaries()
    }

    private fun initPreference() {
        REMINDER = getString(R.string.reminder)
        alarmReminderPreference = findPreference<SwitchPreferenceCompat>(REMINDER) as SwitchPreferenceCompat
    }

    private fun setSummaries() {
        val sharedPref = preferenceManager.sharedPreferences
        alarmReminderPreference.isChecked = sharedPref.getBoolean(REMINDER, false)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == REMINDER) {
            alarmReminderPreference.isChecked = sharedPreferences?.getBoolean(REMINDER, false) as Boolean
        }

        val defValue = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(REMINDER, false)
        setAlarmReminder(defValue)
    }


    private fun setAlarmReminder(state: Boolean) {
        if (state) {
            context?.let { alarmService.setRepeatingAlarm(it) }
        } else {
            context?.let { alarmService.cancelAlarm(it) }
        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
}
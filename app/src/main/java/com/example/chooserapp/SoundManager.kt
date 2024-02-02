package com.example.chooserapp

import android.content.Context
import android.content.SharedPreferences

class SoundManager(private val context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("sound_prefs", Context.MODE_PRIVATE)
    private val soundEnabledKey = "sound_enabled"

    fun isSoundEnabled(): Boolean {
        return preferences.getBoolean(soundEnabledKey, true) // По умолчанию звук включен
    }

    fun enableSound() {
        preferences.edit().putBoolean(soundEnabledKey, true).apply()
        // Включить звук, если это необходимо
    }

    fun disableSound() {
        preferences.edit().putBoolean(soundEnabledKey, false).apply()
        // Выключить звук, если это необходимо
    }
}
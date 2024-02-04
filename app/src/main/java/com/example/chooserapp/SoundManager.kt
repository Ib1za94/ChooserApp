package com.example.chooserapp

import android.content.Context
import android.content.SharedPreferences

class SoundManager(private val context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("sound_prefs", Context.MODE_PRIVATE)
    private val soundEnabledKey = "sound_enabled"
    private val musicEnabledKey = "music_enabled"

    fun isSoundEnabled(): Boolean {
        return preferences.getBoolean(soundEnabledKey, true)
    }

    fun enableSound() {
        preferences.edit().putBoolean(soundEnabledKey, true).apply()
        // Включить звук, если это необходимо
    }

    fun disableSound() {
        preferences.edit().putBoolean(soundEnabledKey, false).apply()
        // Выключить звук, если это необходимо
    }

    fun isMusicEnabled(): Boolean {
        return preferences.getBoolean(musicEnabledKey, true)
    }

    fun enableMusic() {
        preferences.edit().putBoolean(musicEnabledKey, true).apply()
        // Включить музыку, если это необходимо
        // Тут могут быть дополнительные действия по включению музыки
    }

    fun disableMusic() {
        preferences.edit().putBoolean(musicEnabledKey, false).apply()
        // Выключить музыку, если это необходимо
        // Тут могут быть дополнительные действия по выключению музыки
    }
}
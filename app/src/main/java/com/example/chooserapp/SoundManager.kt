package com.example.chooserapp

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager

class SoundManager(private val context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("sound_prefs", Context.MODE_PRIVATE)
    private val soundEnabledKey = "sound_enabled"
    private val musicEnabledKey = "music_enabled"

    fun isSoundEnabled(): Boolean {
        return preferences.getBoolean(soundEnabledKey, true)
    }

    fun enableSound() {
        preferences.edit().putBoolean(soundEnabledKey, true).apply()
        setSystemSoundEnabled(true)
    }

    fun disableSound() {
        preferences.edit().putBoolean(soundEnabledKey, false).apply()
        setSystemSoundEnabled(false)
    }

    fun isMusicEnabled(): Boolean {
        return preferences.getBoolean(musicEnabledKey, true)
    }

    fun enableMusic() {
        preferences.edit().putBoolean(musicEnabledKey, true).apply()
    }

    fun disableMusic() {
        preferences.edit().putBoolean(musicEnabledKey, false).apply()
    }

    private fun setSystemSoundEnabled(enabled: Boolean) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, if (enabled) AudioManager.ADJUST_UNMUTE else AudioManager.ADJUST_MUTE, 0)
    }
}
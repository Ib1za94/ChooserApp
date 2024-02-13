package com.example.chooserapp

import android.content.Context
import android.content.SharedPreferences
import java.util.*

class LocaleManager(private val context: Context) {

    private val sharedPreferencesKey = "language"

    fun getSavedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    // Метод для сохранения выбранного языка
    private fun saveLanguage(context: Context, langCode: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("language", langCode)
        editor.apply()
    }
}


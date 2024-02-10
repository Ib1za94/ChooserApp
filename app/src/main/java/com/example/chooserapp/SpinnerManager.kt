package com.example.chooserapp

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat.recreate
import java.util.Locale

class SpinnerManager(
    private val context: Context,
    private val spinner: Spinner,
    private val sharedPreferencesKey: String
) {

    init {
        setupSpinner()
    }

    fun changeLanguage(context: Context, selectedLanguageCode: String) {
        val locale = Locale(selectedLanguageCode)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        val newContext = context.createConfigurationContext(configuration)
        (context as? Activity)?.recreate()
    }

    private fun setupSpinner() {
        val values: Array<String> = context.resources.getStringArray(R.array.languages).map { it.toString() }.toTypedArray()

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            values
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter

        // Load saved option and set it to the Spinner
        val savedOption = getSavedOption()
        spinner.setSelection(savedOption)

        // Set a listener for the Spinner to save the selected option
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Вызывается при выборе элемента в Spinner
                saveSelectedOption(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Вызывается, если ничего не выбрано
                // Можно добавить обработку, если необходимо
            }
        }
    }

    private fun getSavedOption(): Int {
        val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(sharedPreferencesKey, 0)
    }

    fun saveSelectedOption(position: Int) {
        val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(sharedPreferencesKey, position)
        editor.apply()
    }

}
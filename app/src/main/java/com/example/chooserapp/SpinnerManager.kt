package com.example.chooserapp

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class SpinnerManager(
    private val context: Context,
    private val spinner: Spinner,
    private val sharedPreferencesKey: String
) {

    init {
        setupSpinner()
    }

    private fun setupSpinner() {
        val values: Array<String> = context.resources.getStringArray(R.array.languages)

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
                saveSelectedOption(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle event when nothing is selected if needed
            }
        }
    }

    private fun getSavedOption(): Int {
        val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(sharedPreferencesKey, 0)
    }

    private fun saveSelectedOption(position: Int) {
        val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(sharedPreferencesKey, position)
        editor.apply()
    }
}
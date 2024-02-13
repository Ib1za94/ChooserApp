package com.example.chooserapp

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class SettingsScreenFragment : Fragment() {

    private lateinit var soundManager: SoundManager
    private lateinit var soundCheckBox: CheckBox
    private lateinit var musicCheckBox: CheckBox
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_screen, container, false)
        spinner = view.findViewById(R.id.languageSpinner)

        // Создаем локальную переменную медиаплеера(который инициализирован в активити)
        // в нашем фрагменте.
        val mediaPlayerSet = (requireActivity() as MainActivity).mediaPlayer

        soundManager = SoundManager(requireContext()) // Инициализация SoundManager
        soundCheckBox = view.findViewById(R.id.checkBox_2) // Обработка sound чекбокса
        soundCheckBox.isChecked = soundManager.isSoundEnabled() // Обработка состояния включения sound

        musicCheckBox = view.findViewById(R.id.checkBox_1) // Обработка music чекбокса
        musicCheckBox.isChecked = soundManager.isMusicEnabled() // Обработка состояния включения music

        soundCheckBox?.setOnCheckedChangeListener { _, isChecked ->
            Log.d("CheckBox", "isChecked: $isChecked") // Debug output
            if (isChecked) {
                Log.d("CheckBox", "Sound enabled") // Debug output
                soundManager.enableSound()
                Toast.makeText(context, "Sound is On", Toast.LENGTH_LONG).show()
            } else {
                Log.d("CheckBox", "Sound disabled") // Debug output
                soundManager.disableSound()
                Toast.makeText(context, "Sound is Off", Toast.LENGTH_LONG).show()
            }
        }

        // Обработка чекбокса для музыки
        // Предположим, что у вас есть методы enableMusic и disableMusic в вашем SoundManager
        musicCheckBox.isChecked = soundManager.isMusicEnabled()
        musicCheckBox.setOnCheckedChangeListener { _, isChecked ->
            Log.d("CheckBox", "isChecked: $isChecked")
            if (isChecked) {
                Log.d("CheckBox", "Music enabled")
                soundManager.enableMusic()
                if (!mediaPlayerSet.isPlaying) {
                    mediaPlayerSet.start()
                    Toast.makeText(context, "Music is On", Toast.LENGTH_LONG).show()
                }
            } else {
                Log.d("CheckBox", "Music disabled")
                soundManager.disableMusic()
                if (mediaPlayerSet.isPlaying) {
                    mediaPlayerSet.pause()
                    Toast.makeText(context, "Music is Off", Toast.LENGTH_LONG).show()
                }
            }
        }

        val closeButton: ImageView? = view?.findViewById(R.id.settings_cross)
        closeButton?.setOnClickListener {
            // Закрываем текущий фрагмент
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .remove(this@SettingsScreenFragment)
                .commit()

            // Возвращаемся на главный экран (MainActivity)
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            languages
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLang = parent?.getItemAtPosition(position).toString()
                when (selectedLang) {
                    "English" -> {
                        setLocale(requireActivity(), LANG_ENGLISH)
                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                    "Ukrainian" -> {
                        setLocale(requireActivity(), LANG_UKRAINIAN)
                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                    "Russian" -> {
                        setLocale(requireActivity(), LANG_RUSSIAN)
                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setLocale(activity: Activity, langCode: String) {
        try {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Сохраняем выбранный язык в SharedPreferences
            saveSelectedLanguage(langCode)
        } catch (e: Exception) {
            Log.e("Language", "Error setting locale: ${e.message}")
        }
    }
    // TODO: Эта функция не работает, не сохраняет, нужно доработать
    private fun saveSelectedLanguage(langCode: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("YOUR_PREFERENCES_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("language", langCode)
        editor.apply()

        Log.d("Language", "Saved language: $langCode")
    }

    // TODO:Сделать так, чтобы язык менялся не только во всех кнопках, но и внутри спиннера
    companion object {

        private val LANG_ENGLISH = "en"
        private val LANG_UKRAINIAN = "uk"
        private val LANG_RUSSIAN = "ru"
        private val languages = arrayOf("Select language", "English", "Ukrainian", "Russian")

        @JvmStatic
        fun newInstance() = SettingsScreenFragment()
    }
}
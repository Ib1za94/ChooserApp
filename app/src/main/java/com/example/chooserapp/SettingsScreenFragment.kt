package com.example.chooserapp

import android.content.Context
import android.content.res.ColorStateList
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

class SettingsScreenFragment : Fragment() {

    private lateinit var soundManager: SoundManager
    private lateinit var soundCheckBox: CheckBox
    private lateinit var musicCheckBox: CheckBox
    private lateinit var spinner: Spinner
    private val sharedPreferencesKey = "selectedOption"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_screen, container, false)

        spinner = view.findViewById(R.id.languageSpinner)

        val savedOption = getSavedOption()
        spinner.setSelection(savedOption)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Сохранение выбранного значения при изменении
                saveSelectedOption(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //почему-то без этой функции эррор выбивает, она просто как оболочка должна быть
            }

        }

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
    private fun getSavedOption(): Int {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return sharedPreferences.getInt(sharedPreferencesKey, 0) // 0 - значение по умолчанию
    }

    private fun saveSelectedOption(position: Int) {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(sharedPreferencesKey, position)
        editor.apply()
    }

//  Сделал хуиту, дальше ебану кнопочки на music и sound, ну и попробую поставить код на смену языка
    companion object {

        @JvmStatic
        fun newInstance() = SettingsScreenFragment()
    }
}
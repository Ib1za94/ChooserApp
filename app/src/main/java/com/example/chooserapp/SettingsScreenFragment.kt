package com.example.chooserapp

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
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView

class SettingsScreenFragment : Fragment() {
    private lateinit var soundManager: SoundManager
    private lateinit var soundCheckBox: CheckBox
    private lateinit var musicCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_screen, container, false)

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
            } else {
                Log.d("CheckBox", "Sound disabled") // Debug output
                soundManager.disableSound()
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
            } else {
                Log.d("CheckBox", "Music disabled")
                soundManager.disableMusic()
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
//  Сделал хуиту, дальше ебану кнопочки на music и sound, ну и попробую поставить код на смену языка
    companion object {

        @JvmStatic
        fun newInstance() = SettingsScreenFragment()
    }
}
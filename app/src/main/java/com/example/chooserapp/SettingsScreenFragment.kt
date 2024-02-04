package com.example.chooserapp

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class SettingsScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.setting_screen, container, false)

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
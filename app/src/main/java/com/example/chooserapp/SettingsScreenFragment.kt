package com.example.chooserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

class SettingsScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: Разобраться как повесить онкликлистенер в фрагменте и свернуть его
//        val exitButton: ImageButton = view.findViewById(R.id.crossImageView)
//        exitButton.setOnClickListener{
//            supportFragmentManager.beginTransaction()
//                .remove(SettingsScreenFragment())
//                .commit()
//        }
            // Inflate the layout for this fragment
        return inflater.inflate(R.layout.setting_screen, container, false)


    }

    companion object {

        @JvmStatic
        fun newInstance() = SettingsScreenFragment()
    }
}
package com.example.chooserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class WelcomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.welcome_screen, container, false)

        val getStartedButton : Button = view.findViewById(R.id.welcomeButton)
        getStartedButton.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .replace(R.id.place_holder, GuideScreenFragment())
                .addToBackStack(null)
                .commit()

            // Возвращаемся на главный экран (MainActivity)
//            requireActivity().supportFragmentManager.popBackStack()
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = WelcomeScreenFragment()
            }
    }

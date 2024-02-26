package com.example.chooserapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PlayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
    // Make background black
        view.setBackgroundColor(android.graphics.Color.parseColor("#FF000000"))
    //Make status bar transparent (deprecated, but working)
        activity?.window?.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = android.graphics.Color.TRANSPARENT
            }
        }

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = PlayFragment()
    }
}
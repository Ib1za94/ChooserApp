package com.example.chooserapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView

class PlayFragment : Fragment(), View.OnTouchListener {

    private lateinit var lottieAnimationView: LottieAnimationView
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

        lottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        lottieAnimationView.setOnTouchListener(this)

        return view
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                showAnimation(event.x, event.y)
                return true
            }
        }
        return false
    }

    private fun showAnimation(x: Float, y: Float) {
        // Установи позицию анимации Lottie в место касания
        lottieAnimationView.translationX = x - lottieAnimationView.width / 2
        lottieAnimationView.translationY = y - lottieAnimationView.height / 2

        // Воспроизведи анимацию
        lottieAnimationView.playAnimation()
    }
    companion object {
        @JvmStatic
        fun newInstance() = PlayFragment()
    }
}
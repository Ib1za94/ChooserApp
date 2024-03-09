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

    private lateinit var animationView1: LottieAnimationView
    private lateinit var animationView2: LottieAnimationView

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

        animationView1 = view.findViewById(R.id.animationView)
        animationView2 = view.findViewById(R.id.animationView2)

        // Установим этот фрагмент в качестве слушателя касаний
        view.setOnTouchListener(this)

        return view
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // Обработка событий касания
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                startAnimation(animationView1, event)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // Дополнительные касания
                startAnimation(animationView2, event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                stopAnimation(animationView1)
                stopAnimation(animationView2)
            }
        }

        // Вызываем performClick для обработки события
        v?.performClick()
        return true
    }

    private fun startAnimation(animationView: LottieAnimationView, event: MotionEvent) {
        animationView.visibility = View.VISIBLE
        animationView.x = event.x - animationView.width / 2
        animationView.y = event.y - animationView.height / 2
        animationView.playAnimation()
    }

    private fun stopAnimation(animationView: LottieAnimationView) {
        animationView.cancelAnimation()
        animationView.visibility = View.INVISIBLE
    }
    companion object {
        @JvmStatic
        fun newInstance() = PlayFragment()
    }
}
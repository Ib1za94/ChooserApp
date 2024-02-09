package com.example.chooserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class GuideScreenFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var guideSliderAdapter: ViewPagerAdapter

    // Создаем список фрагментов + создаем фрагменты
    private val fragList = listOf(
        GuideFragment1.newInstance(),
        GuideFragment2.newInstance(),
        GuideFragment3.newInstance()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Переменная для инфлейтера(просто так)
        val view =  inflater.inflate(R.layout.fragment_guide_screen, container, false)

        //Присваиваем переменную для вьюпейджера
        viewPager = view.findViewById(R.id.guideViewPager)
        //Присваиваем переменную для адаптера
        guideSliderAdapter = ViewPagerAdapter(this, fragList)
        // Ставим адаптер для переменной viewPager через метод .adapter(не из либы)
        viewPager.adapter = guideSliderAdapter

        //Переменная для вьюхи точечек (называется indicator) --- это уже код с самой либы сторонней
        val indicator = view.findViewById<CircleIndicator3>(R.id.indicator)
        // Тут используем метод из либы .setViewPager чтоб подконнектить точки
        // и сам вьюпейджер
        indicator.setViewPager(viewPager)

        //Онкликлистенер для кнопки скип
        val skipButton = view.findViewById<Button>(R.id.skipButton)
        skipButton.setOnClickListener{
            //Закрываем текущий фрагмент
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .remove(this@GuideScreenFragment)
                .commit()

            // Возвращаемся на главный экран (MainActivity)
//            requireActivity().supportFragmentManager.popBackStack()

            // Закрываем WelcomeScreenFragment через тег
            val welcomeFragment = requireActivity().supportFragmentManager.findFragmentByTag("WelcomeScreenFragment")
            // ?.let проверяет сперва на null "?" а let == запускает часть кода только если фрагмент non null
            welcomeFragment?.let {
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    .remove(it)
                    .commit()
            }


        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = GuideScreenFragment()
    }
}
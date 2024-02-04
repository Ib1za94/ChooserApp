package com.example.chooserapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


// Использовать для кода если хостить в гайдАктивити а не в ГайдФрагмент
// class ViewPagerAdapter(activity: FragmentActivity, private val list: List<Fragment>): FragmentStateAdapter(activity) {
class ViewPagerAdapter(activity: Fragment, private val list: List<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        // тут получаем интеджер сколько нужно страничек быть у вьюпейджера
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        // создаем фрагмент в зависимости от его позиции в списке
        return list[position]
    }
}
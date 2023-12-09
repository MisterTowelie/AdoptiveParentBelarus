package com.github.adoptiveparent.screens

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentsList: List<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }

}
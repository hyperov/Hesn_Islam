package com.islam.hesn.myapplication.youtube.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> YoutubeFirstChannelFragment()
            1 -> YoutubeSecondChannelFragment()
            else -> YoutubeFirstChannelFragment()
        }

}
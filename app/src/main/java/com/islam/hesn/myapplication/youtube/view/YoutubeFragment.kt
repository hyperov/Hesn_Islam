package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.islam.hesn.myapplication.R
import kotlinx.android.synthetic.main.fragment_youtube.*

class YoutubeFragment : Fragment() {

    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )

        pagerAdapter = ViewPagerAdapter(this)
        pager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.main_channel)
                1 -> tab.text = getString(R.string.education_channel)
            }

        }.attach()
    }

}
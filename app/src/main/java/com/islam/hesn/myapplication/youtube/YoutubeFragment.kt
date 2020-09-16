package com.islam.hesn.myapplication.youtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import kotlinx.android.synthetic.main.fragment_youtube.*

class YoutubeFragment : Fragment() {

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: YoutubeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pagerAdapter = ViewPagerAdapter(this)
        pager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.education_channel)
                1 -> tab.text = getString(R.string.main_channel)
            }

        }.attach()
    }

}
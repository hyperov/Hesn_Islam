package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoutubeSecondChannelFragment : Fragment() {

    private val youtubeViewModel: YoutubeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_first_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        getVideos()
    }

    private fun getVideos() {
        youtubeViewModel.getYoutubeChannelVideos(getString(R.string.main_channel_id))
    }

    private fun observeData() {
        youtubeViewModel.apply {

            nextPage.observe(viewLifecycleOwner, { nxtPage -> })
            videoList.observe(viewLifecycleOwner, { videos -> })
        }

    }

}
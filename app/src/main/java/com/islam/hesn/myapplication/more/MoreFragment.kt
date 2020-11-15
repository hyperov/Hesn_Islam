package com.islam.hesn.myapplication.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_more.*

@AndroidEntryPoint
class MoreFragment : Fragment(), View.OnClickListener {

    private val moreViewModel: MoreViewModel by viewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvAboutUs.setOnClickListener(this)
        tvContactUs.setOnClickListener(this)
        tvLastRead.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvAboutUs -> {
                youtubePlayerViewModel.videoId.value = getString(R.string.about_us_video_id)
                youtubePlayerViewModel.videoTitle.value = getString(R.string.about_us_video_title)
                findNavController().navigate(R.id.youtubePlayerFragment)
            }
            tvContactUs -> {
            }
            tvLastRead -> {
            }
        }
    }


}
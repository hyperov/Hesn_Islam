package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.fragment.compose.content
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YoutubeHostFragment : Fragment() {

    private val youtubeViewModel: YoutubeViewModel by viewModels()
    private val youtubeSearchViewModel: YoutubeSearchViewModel by viewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = content {
        YoutubeRootScreen(
            youtubeViewModel = youtubeViewModel,
            youtubeSearchViewModel = youtubeSearchViewModel,
            youtubePlayerViewModel = youtubePlayerViewModel,
        )
    }
}


package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.home.IS_CONNECTED
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.openYoutubeChannelIntent
import com.islam.hesn.myapplication.utils.showSnackBar
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_youtube_first_channel.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YoutubeSecondChannelFragment : Fragment() {

    private val youtubeViewModel: YoutubeViewModel by viewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    private val pagingAdapter =
        YoutubeRecyclerViewPagingAdapter(VideoComparator) { videoId, videoTitle ->
            youtubePlayerViewModel.videoId.value = videoId
            youtubePlayerViewModel.videoTitle.value = videoTitle
            findNavController().navigate(R.id.youtubePlayerFragment)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_youtube_first_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoList.adapter = pagingAdapter
        setRefreshListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getVideos()
    }

    private fun getVideos() {
        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

            val bottomNavView: BottomNavigationView =
                activity?.findViewById(R.id.bottomNavigation)!!

            requireContext().showSnackBar(videoList,
                bottomNavView,
                getString(R.string.error_no_connection),
                android.R.color.holo_red_light)
        }
        youtubeViewModel.getYoutubeChannelVideos(getString(R.string.education_channel_playlist_id))
        getPagingMovies()
    }

    private fun setRefreshListener() {
        refresh.setOnRefreshListener {
            pagingAdapter.refresh()
            refresh.isRefreshing = false
        }
    }

    private fun getPagingMovies() {

        viewLifecycleOwner.lifecycleScope.launch {

            youtubeViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {

            pagingAdapter.loadStateFlow.collectLatest { loadStates ->

                progressYoutube.isVisible = loadStates.refresh is LoadState.Loading
                videoList.isVisible = loadStates.refresh is LoadState.NotLoading
                videoList.isVisible = loadStates.refresh !is LoadState.Error
                error.isVisible = loadStates.refresh is LoadState.Error
                errorText.isVisible = loadStates.refresh is LoadState.Error
                if (loadStates.refresh is LoadState.Error) {
                    val error = (loadStates.refresh as LoadState.Error).error
                    if (error.message!!.contains("quotaExceeded")) {
                        openYoutubeChannelIntent(getString(R.string.education_channel_playlist_id))
                    }
                }
            }

        }
    }


}
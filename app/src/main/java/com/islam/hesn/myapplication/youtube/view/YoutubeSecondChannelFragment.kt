package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_youtube_first_channel.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YoutubeSecondChannelFragment : Fragment() {

    private val youtubeViewModel: YoutubeViewModel by viewModels()
    private val pagingAdapter = YoutubeRecyclerViewPagingAdapter(VideoComparator)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_youtube_first_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videosList.adapter = pagingAdapter
        getVideos()
    }

    private fun getVideos() {
        youtubeViewModel.getYoutubeChannelVideos(getString(R.string.main_channel_id))
        getPagingMovies()
    }

    private fun getPagingMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            youtubeViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                progressYoutube.isVisible = loadStates.refresh is LoadState.Loading
                videosList.isVisible = loadStates.refresh is LoadState.NotLoading
//                retry.isVisible = loadStates.refresh !is LoadState.Loading
//                errorMsg.isVisible = loadState.refresh is LoadState.Error
            }
        }
    }

}
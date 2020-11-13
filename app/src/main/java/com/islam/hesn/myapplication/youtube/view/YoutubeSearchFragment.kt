package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.home.changeToolbarTitle
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_youtube_first_channel.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class YoutubeSearchFragment : Fragment() {

    private var channelId: String = ""
    private val youtubeSearchViewModel: YoutubeSearchViewModel by activityViewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    private val pagingAdapter =
        YoutubeRecyclerViewSearchPagingAdapter(VideoSearchComparator) { videoId, videoTitle ->
            youtubePlayerViewModel.videoId.value = videoId
            youtubePlayerViewModel.videoTitle.value = videoTitle
            findNavController().navigate(R.id.youtubePlayerFragment)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.youtube_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeToolbarTitle(youtubeSearchViewModel.searchQuery.value + "فى :" + when (youtubeSearchViewModel.selectedTabPosition.value) {
            0 -> getString(R.string.main_channel)
            1 -> getString(R.string.education_channel)
            else -> ""
        })
        videosList.adapter = pagingAdapter
        channelId = when (youtubeSearchViewModel.selectedTabPosition.value) {
            0 -> getString(R.string.main_channel_id)
            1 -> getString(R.string.education_channel_id)
            else -> ""
        }
        youtubeSearchViewModel.getSearchedYoutubeVideos(channelId)
        getPagingMovies()
    }



    private fun getPagingMovies() {
        viewLifecycleOwner.lifecycleScope.launch {

            pagingAdapter.loadStateFlow.collectLatest { loadStates ->

                progressYoutube.isVisible = loadStates.refresh is LoadState.Loading
                videosList.isVisible = loadStates.refresh is LoadState.NotLoading
//                retry.isVisible = loadStates.refresh !is LoadState.Loading
                videosList.isVisible = loadStates.refresh !is LoadState.Error
//                if (loadStates.refresh is LoadState.Error) openYoutubeChannelIntent()
//                errorMsg.isVisible = loadStates.refresh is LoadState.Error
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            youtubeSearchViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

    }

}
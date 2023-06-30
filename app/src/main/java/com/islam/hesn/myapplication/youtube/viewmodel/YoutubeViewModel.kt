package com.islam.hesn.myapplication.youtube.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.islam.hesn.myapplication.youtube.model.repo.YoutubePagingSource
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeRepo
import com.islam.hesn.myapplication.youtube.model.response.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class YoutubeViewModel @Inject constructor(private val repo: YoutubeRepo) : ViewModel() {

    lateinit var flow: Flow<PagingData<Video>>

    fun getYoutubeChannelVideos(channelPlayListId: String) {

        flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10, 3)
        ) {
            YoutubePagingSource(repo, channelPlayListId)
        }.flow
            .cachedIn(viewModelScope)

    }

}
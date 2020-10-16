package com.islam.hesn.myapplication.youtube.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.islam.hesn.myapplication.youtube.model.repo.YoutubePagingSource
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeRepo
import com.islam.hesn.myapplication.youtube.model.response.Video
import kotlinx.coroutines.flow.Flow

class YoutubeViewModel @ViewModelInject constructor(private val repo: YoutubeRepo) : ViewModel() {

    lateinit var flow: Flow<PagingData<Video>>

    fun getYoutubeChannelVideos(channelId: String) {

        flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10)
        ) {
            YoutubePagingSource(repo, channelId)
        }.flow
            .cachedIn(viewModelScope)

    }
}
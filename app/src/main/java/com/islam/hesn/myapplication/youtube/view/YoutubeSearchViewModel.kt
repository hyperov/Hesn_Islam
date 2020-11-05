package com.islam.hesn.myapplication.youtube.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
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

class YoutubeSearchViewModel @ViewModelInject constructor(private val repo: YoutubeRepo) :
    ViewModel() {

    val searchQuery = MutableLiveData<String>()
    val selectedTabPosition = MutableLiveData<Int>()

    lateinit var flow: Flow<PagingData<Video>>

    fun getSearchedYoutubeVideos(channelId: String) {

        flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10)
        ) {
            YoutubePagingSource(repo, channelId, true, searchQuery.value!!)
        }.flow
            .cachedIn(viewModelScope)

    }
}
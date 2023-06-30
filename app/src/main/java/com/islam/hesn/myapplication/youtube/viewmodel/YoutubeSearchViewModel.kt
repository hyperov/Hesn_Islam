package com.islam.hesn.myapplication.youtube.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeRepo
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeSearchPagingSource
import com.islam.hesn.myapplication.youtube.model.response.SearchVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class YoutubeSearchViewModel @Inject constructor(private val repo: YoutubeRepo) :
    ViewModel() {

    val searchQuery = MutableLiveData<String>()
    val selectedTabPosition = MutableLiveData<Int>()

    lateinit var flow: Flow<PagingData<SearchVideo>>

    fun getSearchedYoutubeVideos(channelId: String) {

        flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 10, 2)
        ) {
            YoutubeSearchPagingSource(repo, channelId, searchQuery.value!!)
        }.flow
            .cachedIn(viewModelScope)

    }
}
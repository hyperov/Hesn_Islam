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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class YoutubeChannelUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class YoutubeViewModel @Inject constructor(private val repo: YoutubeRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(YoutubeChannelUiState())
    val uiState: StateFlow<YoutubeChannelUiState> = _uiState.asStateFlow()

    // Use StateFlow to hold the Flow so it can be observed and updated
    private val _flowState = MutableStateFlow<Flow<PagingData<Video>>>(flowOf(PagingData.empty()))
    val flowState: StateFlow<Flow<PagingData<Video>>> = _flowState.asStateFlow()

    fun getYoutubeChannelVideos(channelPlayListId: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        val newFlow = Pager(
            PagingConfig(pageSize = 10, prefetchDistance = 3)
        ) {
            YoutubePagingSource(repo, channelPlayListId)
        }.flow
            .cachedIn(viewModelScope)

        _flowState.value = newFlow

        // Paging will drive loading/error; uiState can be extended if needed
        _uiState.update { it.copy(isLoading = false) }
    }
}
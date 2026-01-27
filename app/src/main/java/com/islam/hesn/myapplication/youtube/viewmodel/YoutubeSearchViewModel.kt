package com.islam.hesn.myapplication.youtube.viewmodel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class YoutubeSearchUiState(
    val searchQuery: String = "",
    val selectedTabPosition: Int = 0,
    val isLoading: Boolean = false,
)

@HiltViewModel
class YoutubeSearchViewModel @Inject constructor(private val repo: YoutubeRepo) :
    ViewModel() {

    private val _uiState = MutableStateFlow(YoutubeSearchUiState())
    val uiState: StateFlow<YoutubeSearchUiState> = _uiState.asStateFlow()

    // Use StateFlow to hold the Flow so it can be observed and updated
    private val _flowState = MutableStateFlow<Flow<PagingData<SearchVideo>>>(flowOf(PagingData.empty()))
    val flowState: StateFlow<Flow<PagingData<SearchVideo>>> = _flowState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun updateSelectedTab(position: Int) {
        _uiState.update { it.copy(selectedTabPosition = position) }
    }

    fun getSearchedYoutubeVideos(channelId: String) {
        val query = _uiState.value.searchQuery

        if (query.isBlank()) return

        _uiState.update { it.copy(isLoading = true) }

        val newFlow = Pager(
            PagingConfig(pageSize = 10, prefetchDistance = 2)
        ) {
            YoutubeSearchPagingSource(repo, channelId, query)
        }.flow
            .cachedIn(viewModelScope)

        _flowState.value = newFlow

        _uiState.update { it.copy(isLoading = false) }
    }
}
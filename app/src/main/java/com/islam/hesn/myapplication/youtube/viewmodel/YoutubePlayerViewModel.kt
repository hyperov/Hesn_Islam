package com.islam.hesn.myapplication.youtube.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class YoutubePlayerUiState(
    val videoId: String? = null,
    val videoTitle: String? = null,
)

@HiltViewModel
class YoutubePlayerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(YoutubePlayerUiState())
    val uiState: StateFlow<YoutubePlayerUiState> = _uiState.asStateFlow()

    fun setVideo(id: String, title: String) {
        _uiState.update { it.copy(videoId = id, videoTitle = title) }
    }
}
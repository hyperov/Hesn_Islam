package com.islam.hesn.myapplication.youtube.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.youtube.model.repo.YoutubeRepo
import com.islam.hesn.myapplication.youtube.model.response.Video
import kotlinx.coroutines.launch

class YoutubeViewModel @ViewModelInject constructor(private val repo: YoutubeRepo) : ViewModel() {

    val nextPage = MutableLiveData<String>()
    val videoList = MutableLiveData<List<Video>>()

    val loading = MutableLiveData<Boolean>()

    fun getYoutubeChannelVideos(channelId: String) {
        loading.value = true
        viewModelScope.launch {
            repo.getYoutubeChannelVideos(channelId)
                .apply {
                    nextPage.value = nextPageToken
                    videoList.value = items
                    loading.value = false
                }
        }
    }
}
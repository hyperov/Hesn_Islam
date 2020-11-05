package com.islam.hesn.myapplication.youtube.model.repo

import com.islam.hesn.myapplication.youtube.model.response.YoutubeSearchVideosResponse
import com.islam.hesn.myapplication.youtube.model.response.YoutubeVideosResponse

interface YoutubeRepo {

    suspend fun getYoutubeChannelVideos(playlistId: String, nextPage: String): YoutubeVideosResponse

    suspend fun getSearchedYoutubeVideos(
        searchQuery: String,
        channelId: String,
        nextPage: String,
    ): YoutubeSearchVideosResponse
}
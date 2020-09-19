package com.islam.hesn.myapplication.youtube.model.repo

import com.islam.hesn.myapplication.youtube.model.response.YoutubeVideosResponse
import javax.inject.Inject

class YoutubeRepoImpl @Inject constructor(
    private val apis: YoutubeApis,
    private val apiKey: String
) :
    YoutubeRepo {

    override suspend fun getYoutubeChannelVideos(
        playlistId: String
    ): YoutubeVideosResponse {
        return apis.getYoutubeChannelVideos(playlistId, apiKey)
    }
}
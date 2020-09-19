package com.islam.hesn.myapplication.youtube.model.repo

import com.islam.hesn.myapplication.youtube.model.response.YoutubeVideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApis {


    @GET("youtube.playlistItems.list")
    suspend fun getYoutubeChannelVideos(
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("part") part: String = "snippet"
    ): YoutubeVideosResponse


    companion object {
        const val BASE_URL = "https://developers.google.com/apis-explorer/#p/youtube/v3/"

    }

}
package com.islam.hesn.myapplication.youtube.model.repo

import com.islam.hesn.myapplication.youtube.model.response.YoutubeVideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApis {


    @GET("playlistItems")
    suspend fun getYoutubeChannelVideos(
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 10
    ): YoutubeVideosResponse


    companion object {
        const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    }

}
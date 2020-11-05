package com.islam.hesn.myapplication.youtube.model.repo

import com.islam.hesn.myapplication.youtube.model.response.YoutubeSearchVideosResponse
import com.islam.hesn.myapplication.youtube.model.response.YoutubeVideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApis {


    @GET("playlistItems")
    suspend fun getYoutubeChannelVideos(
        @Query("playlistId") playlistId: String,
        @Query("key") key: String,
        @Query("pageToken") pageToken: String = "",
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 10,
    ): YoutubeVideosResponse

    @GET("search")
    suspend fun getSearchedYoutubeVideos(
        @Query("q") searchQuery: String,
        @Query("channelId") channelId: String,
        @Query("key") key: String,
        @Query("pageToken") pageToken: String = "",
        @Query("type") type: String = "video",
        @Query("order") order: String = "date",
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 10,
    ): YoutubeSearchVideosResponse


    companion object {
        const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    }

}
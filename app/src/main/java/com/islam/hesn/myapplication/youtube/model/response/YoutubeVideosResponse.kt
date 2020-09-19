package com.islam.hesn.myapplication.youtube.model.response

data class YoutubeVideosResponse(
    val items: List<Video>?,
    val nextPageToken: String?,
    val pageInfo: PageInfo?,
    val error: ErrorRes?
)
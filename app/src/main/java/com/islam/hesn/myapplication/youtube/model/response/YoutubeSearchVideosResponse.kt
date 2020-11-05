package com.islam.hesn.myapplication.youtube.model.response

data class YoutubeSearchVideosResponse(
    val items: List<SearchVideo>?,
) : CommonYoutubeResponse()
package com.islam.hesn.myapplication.youtube.model.response

data class YoutubeVideosResponse(
    val items: List<Video>?
):CommonYoutubeResponse()
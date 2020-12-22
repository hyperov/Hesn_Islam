package com.islam.hesn.myapplication.youtube.model.response

open class CommonYoutubeResponse(
    val nextPageToken: String?="",
    val error: ErrorRes?=null,
)
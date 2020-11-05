package com.islam.hesn.myapplication.youtube.model.response

open class CommonYoutubeResponse(
    val nextPageToken: String?="",
    val pageInfo: PageInfo?=null,
    val error: ErrorRes?=null,
)
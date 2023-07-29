package com.islam.hesn.myapplication.bible.model.response.bible

import com.google.gson.annotations.SerializedName

data class Chapter(
    @field:SerializedName("chapter")
    val chapterNum: Int,
    @field:SerializedName("verses")
    val verses: List<Verse>
)
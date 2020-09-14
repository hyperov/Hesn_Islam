package com.islam.hesn.myapplication.bible.model.response

import com.google.gson.annotations.SerializedName

data class Chapter(
    @field:SerializedName("chapter_nr")
    val chapterNum: Int,
    @field:SerializedName("chapter")
    val verseMap: Map<String, Verse>
)
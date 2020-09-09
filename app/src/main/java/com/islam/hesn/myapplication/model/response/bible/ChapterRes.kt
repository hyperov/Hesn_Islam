package com.islam.hesn.myapplication.model.response.bible

import com.google.gson.annotations.SerializedName

data class ChapterRes(
    val chapter: Chapter,
    @field:SerializedName("chapter_nr")
    val chapterNum: Int
)
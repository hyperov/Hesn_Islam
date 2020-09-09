package com.islam.hesn.myapplication.model.response.bible

import com.google.gson.annotations.SerializedName

data class VerseRes(
    @field:SerializedName("verse_nr")
    val verseNum: Int,
    val verse: String
)
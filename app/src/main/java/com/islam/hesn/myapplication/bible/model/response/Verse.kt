package com.islam.hesn.myapplication.bible.model.response

import com.google.gson.annotations.SerializedName

data class Verse(
    @field:SerializedName("verse_nr")
    val verseNum: Int,
    @field:SerializedName("verse")
    val verseContent: String
)
package com.islam.hesn.myapplication.bible.model.response.bible

import com.google.gson.annotations.SerializedName

data class BibleResponse(
    val direction: String,
    val type: String,
    @field:SerializedName("version")
    val booksMap: Map<String, Book>,
    @field:SerializedName("version_ref")
    val translationName: String
)
package com.islam.hesn.myapplication.bible.model.response.bible

import com.google.gson.annotations.SerializedName

data class Book(
    @field:SerializedName("abbreviation")
    val translationName: String,
    @field:SerializedName("language")
    val language: String,
    @field:SerializedName("name")
    val bookName: String,
    @field:SerializedName("nr")
    val bookNum: Int,
    @field:SerializedName("url")
    val bookUrl: String?,
    @field:SerializedName("chapters")
    val chapters: List<Chapter>
)
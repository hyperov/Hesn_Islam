package com.islam.hesn.myapplication.bible.model.response.bible

import com.google.gson.annotations.SerializedName

data class Book(
    @field:SerializedName("version")
    val translationName: String,
    @field:SerializedName("book_name")
    val bookName: String,
    @field:SerializedName("book_nr")
    val bookNum: Int,
    val direction: String,
    @field:SerializedName("book")
    val chaptersMap: Map<String, Chapter>,
)
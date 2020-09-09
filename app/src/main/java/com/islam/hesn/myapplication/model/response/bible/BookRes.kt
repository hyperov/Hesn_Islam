package com.islam.hesn.myapplication.model.response.bible

import com.google.gson.annotations.SerializedName

data class BookRes(
    @field:SerializedName("book_name")
    val bookName: String,
    val direction: String,
    @field:SerializedName("version")
    val translationName: String,
    @field:SerializedName("book_nr")
    val bookNum: Int,
    val book: Book
)
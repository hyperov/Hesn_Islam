package com.islam.hesn.myapplication.bible.model.response.search

import com.google.gson.annotations.SerializedName
import com.islam.hesn.myapplication.bible.model.response.bible.Book


data class BibleSearchRes(
    @field:SerializedName("abbreviation")
    val translationName: String,
    @field:SerializedName("language")
    val language: String,
    @field:SerializedName("books")
    val books: List<Book>
    )


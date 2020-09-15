package com.islam.hesn.myapplication.bible.model.response.translation

import com.google.gson.annotations.SerializedName
import com.islam.hesn.myapplication.bible.model.response.bible.Verse

data class Book(
    @field:SerializedName("book_name")
    val bookName: String,
    @field:SerializedName("book_nr")
    val bookNum: String,
    @field:SerializedName("book_ref")
    val bookRef: String,
    @field:SerializedName("chapter")
    val verseMap: Map<String, Verse>,
    @field:SerializedName("chapter_nr")
    val chapterNum: String
)
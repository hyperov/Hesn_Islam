package com.islam.hesn.myapplication.bible.model.response.translation

data class BibleVerseTranslationResponse(
    val book: List<Book>,
    val direction: String,
    val type: String,
    val version: String
)
package com.islam.hesn.myapplication.search.model

import com.islam.hesn.myapplication.bible.model.response.bible.Verse


data class SearchedVerse(
    val bookName: String,
    val chapterName: String,
    val verseList: List<Verse>,
    val bookNameEn: String,
)

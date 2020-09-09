package com.islam.hesn.myapplication.model.response.bible

data class Book(
    val chaptersMap: Map<String, ChapterRes>
)
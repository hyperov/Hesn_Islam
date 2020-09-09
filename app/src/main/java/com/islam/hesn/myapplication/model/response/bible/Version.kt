package com.islam.hesn.myapplication.model.response.bible

data class Version(
    val booksMap: Map<String, BookRes>
)
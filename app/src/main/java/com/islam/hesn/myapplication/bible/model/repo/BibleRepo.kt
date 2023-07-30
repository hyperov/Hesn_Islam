package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import kotlinx.coroutines.flow.Flow

interface BibleRepo {

    fun getBibleBooks(translation: String): Flow<Map<String, Book>>

    suspend fun getBibleBookChapters(
        translation: String,
        bookNum: String
    ): Flow<Book>

    suspend fun getTranslatedVerse(
        translation: String,
        book: String,
        chapter: String,
        verseNum : Int
    ): Flow<Verse>
}
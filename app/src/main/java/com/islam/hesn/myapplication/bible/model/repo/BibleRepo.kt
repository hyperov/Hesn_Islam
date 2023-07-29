package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.translation.BibleVerseTranslationResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface BibleRepo {

    fun getBibleBooks(translation: String): Flow<Map<String, Book>>

    suspend fun getBibleBook(
        translation: String,
        bookNum: String
    ): Flow<Book>

    suspend fun getTranslatedVerse(
        translation: String,
        passage: String,
    ): BibleVerseTranslationResponse
}
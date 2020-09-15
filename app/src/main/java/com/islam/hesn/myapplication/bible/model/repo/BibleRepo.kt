package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.BibleResponse
import com.islam.hesn.myapplication.bible.model.response.translation.BibleVerseTranslationResponse

interface BibleRepo {

    suspend fun getBible(translation: String): BibleResponse

    suspend fun getTranslatedVerse(
        translation: String,
        passage: String
    ): BibleVerseTranslationResponse
}
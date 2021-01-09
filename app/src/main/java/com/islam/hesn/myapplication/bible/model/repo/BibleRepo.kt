package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.BibleResponse
import com.islam.hesn.myapplication.bible.model.response.translation.BibleVerseTranslationResponse
import io.reactivex.rxjava3.core.Flowable

interface BibleRepo {

    fun getBible(translation: String): Flowable<BibleResponse>

    suspend fun getTranslatedVerse(
        translation: String,
        passage: String,
    ): BibleVerseTranslationResponse
}
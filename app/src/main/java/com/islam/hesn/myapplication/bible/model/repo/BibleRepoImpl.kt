package com.islam.hesn.myapplication.bible.model.repo

import com.google.gson.Gson
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.translation.BibleVerseTranslationResponse
import com.islam.hesn.myapplication.utils.replaceCustom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BibleRepoImpl @Inject constructor(private val apis: BibleApis) : BibleRepo {

    override fun getBibleBooks(translation: String): Flow<Map<String, Book>> {

        return flow { emit(apis.getBibleBooks(translation)) }
    }

    override suspend fun getBibleBook(translation: String, bookNum: String): Flow<Book> =

        flowOf(apis.getBibleBook(translation, bookNum))


    override suspend fun getTranslatedVerse(
        translation: String, passage: String,
    ): BibleVerseTranslationResponse {

        val clean: String = apis.getTranslatedVerse(translation, passage)
            .replaceCustom("(", "")
            .replaceCustom(")", "")
            .replaceCustom(";", "")

        return Gson().fromJson(clean, BibleVerseTranslationResponse::class.java)
    }

}
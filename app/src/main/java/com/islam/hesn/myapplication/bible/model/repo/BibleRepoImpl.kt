package com.islam.hesn.myapplication.bible.model.repo

import com.google.gson.Gson
import com.islam.hesn.myapplication.bible.model.response.bible.BibleResponse
import com.islam.hesn.myapplication.bible.model.response.translation.BibleVerseTranslationResponse
import com.islam.hesn.myapplication.utils.replaceCustom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BibleRepoImpl @Inject constructor(private val apis: BibleApis) : BibleRepo {

    override fun getBible(translation: String): Flow<BibleResponse> {

        return flow { emit(apis.getBible(translation)) }
            .map { StringBuilder(it).removeSurrounding("(", ");").toString() }
            .map { Gson().fromJson(it, BibleResponse::class.java) }
    }

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
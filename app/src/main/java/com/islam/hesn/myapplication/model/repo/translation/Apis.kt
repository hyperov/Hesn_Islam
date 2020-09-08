package com.islam.hesn.myapplication.model.repo.translation

import com.islam.hesn.myapplication.model.response.translation.AyaTranslationItem
import retrofit2.http.GET
import retrofit2.http.Path

interface Apis {

    @GET("{translation_key}/{sura_number}/{aya_number}")
    suspend fun getAyat(
        @Path("translation_key") translation: String,
        @Path("sura_number") suraNum: Int,
        @Path("aya_number") ayaNum: Int
    ): AyaTranslationItem


    companion object {
        const val BASE_URL = "https://quranenc.com/api/translation/aya/"

    }

}
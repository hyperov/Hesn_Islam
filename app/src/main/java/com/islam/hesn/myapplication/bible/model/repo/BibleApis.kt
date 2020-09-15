package com.islam.hesn.myapplication.bible.model.repo

import retrofit2.http.GET
import retrofit2.http.Query

interface BibleApis {

    @GET("json")
    suspend fun getBible(@Query("translation") translation: String): String

    @GET("json")
    suspend fun getTranslatedVerse(
        @Query("translation") translation: String,
        @Query("passage") passage: String
    ): String


    companion object {
        const val BASE_URL = "https://getbible.net/"

    }

}
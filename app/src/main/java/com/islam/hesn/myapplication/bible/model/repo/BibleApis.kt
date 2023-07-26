package com.islam.hesn.myapplication.bible.model.repo

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BibleApis {

    @GET("v2/{translation}.json")
    suspend fun getBible(@Path("translation") translation: String): String

    //السفر
    @GET("v2/{translation}/{book}.json")
    suspend fun getBibleBook(@Path("translation") translation: String,
                             @Path("book") book: String): String

    //الفصل
    @GET("v2/{translation}/{book}/{chapter}.json")
    suspend fun getBibleChapter(@Path("translation") translation: String,
                                @Path("book") book: String,
                                @Path("chapter") chapter: String): String

    @GET("v2/{translation}")
    suspend fun getTranslatedVerse(
        @Path("translation") translation: String,
        @Path("passage") passage: String,
    ): String


    companion object {
        const val BASE_URL = "https://api.getbible.net/"

    }

}
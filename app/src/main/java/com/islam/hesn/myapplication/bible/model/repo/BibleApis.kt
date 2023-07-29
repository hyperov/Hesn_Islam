package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface BibleApis {

    @GET("{translation}/books.json")
    suspend fun getBibleBooks(@Path("translation") translation: String): Map<String, Book>

    //السفر
    @GET("{translation}/{book}.json")
    suspend fun getBibleBook(
        @Path("translation") translation: String,
        @Path("book") bookNum: String
    ): Book

    //الفصل
    @GET("{translation}/{book}/{chapter}.json")
    suspend fun getBibleChapter(
        @Path("translation") translation: String,
        @Path("book") book: String,
        @Path("chapter") chapter: String
    ): String

    @GET("{translation}")
    suspend fun getTranslatedVerse(
        @Path("translation") translation: String,
        @Path("passage") passage: String,
    ): String


    companion object {
        const val BASE_URL = "https://api.getbible.net/v2/"

    }

}
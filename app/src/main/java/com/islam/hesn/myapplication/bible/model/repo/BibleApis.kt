package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import retrofit2.http.GET
import retrofit2.http.Path

interface BibleApis {

    //الاسفار
    @GET("{translation}/books.json")
    suspend fun getBibleBooks(@Path("translation") translation: String): Map<String, Book>

    //السفر باصحاحاته
    @GET("{translation}/{book}.json")
    suspend fun getBibleBookChapters(
        @Path("translation") translation: String,
        @Path("book") bookNum: String
    ): Book

    //الفصل
    @GET("{translation}/{book}/{chapter}.json")
    suspend fun getBibleTranslatedChapter(
        @Path("translation") translation: String,
        @Path("book") book: String,
        @Path("chapter") chapter: String
    ): Chapter


    companion object {
        const val BASE_URL = "https://api.getbible.net/v2/"

    }

}
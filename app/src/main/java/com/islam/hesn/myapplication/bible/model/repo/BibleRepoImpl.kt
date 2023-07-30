package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.bible.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BibleRepoImpl @Inject constructor(private val apis: BibleApis) : BibleRepo {

    override fun getBibleBooks(translation: String): Flow<Map<String, Book>> {

        return flow { emit(apis.getBibleBooks(translation)) }
    }

    override suspend fun getBibleBookChapters(translation: String, bookNum: String): Flow<Book> =

        flowOf(apis.getBibleBookChapters(translation, bookNum))


    override suspend fun getTranslatedVerse(
        translation: String, book: String, chapter: String,
        verseNum: Int
    ) =
        flowOf(
            apis.getBibleTranslatedChapter(
                translation,
                book,
                chapter
            )
        ).map { it.verses[verseNum - 1] }

}
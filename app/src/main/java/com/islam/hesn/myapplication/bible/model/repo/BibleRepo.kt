package com.islam.hesn.myapplication.bible.model.repo

import com.islam.hesn.myapplication.bible.model.response.BibleResponse

interface BibleRepo {

    suspend fun getBible(translation: String): BibleResponse
}
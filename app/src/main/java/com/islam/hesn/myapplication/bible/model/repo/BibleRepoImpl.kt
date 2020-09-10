package com.islam.hesn.myapplication.bible.model.repo

import com.google.gson.Gson
import com.islam.hesn.myapplication.bible.model.response.BibleResponse
import javax.inject.Inject

class BibleRepoImpl @Inject constructor(private val apis: BibleApis) : BibleRepo {

    override suspend fun getBible(translation: String): BibleResponse {

        val clean: String = apis.getBible(translation)
            .replace("(", "").replace(")", "")
            .replace(";", "")

        return Gson().fromJson(clean, BibleResponse::class.java)
    }


}
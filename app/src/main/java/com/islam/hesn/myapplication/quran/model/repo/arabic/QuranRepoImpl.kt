package com.islam.hesn.myapplication.quran.model.repo.arabic

import android.content.res.AssetManager
import com.google.gson.Gson
import com.islam.hesn.myapplication.quran.model.response.arabic.QuranBaseResponse
import com.islam.hesn.myapplication.utils.arabicFile
import com.islam.hesn.myapplication.utils.readJsonStringFromAssets
import javax.inject.Inject

class QuranRepoImpl @Inject constructor(private val assets: AssetManager) : QuranRepo {

    override fun getAllArabicSurah(): QuranBaseResponse {
        val jsonString = assets.readJsonStringFromAssets(arabicFile)
        return Gson().fromJson(jsonString, QuranBaseResponse::class.java)
    }
}
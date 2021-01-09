package com.islam.hesn.myapplication.quran.model.repo.arabic

import android.content.res.AssetManager
import com.google.gson.Gson
import com.islam.hesn.myapplication.quran.model.response.arabic.QuranBaseResponse
import com.islam.hesn.myapplication.utils.*
import javax.inject.Inject

class QuranRepoImpl @Inject constructor(private val assets: AssetManager) : QuranRepo {

    override suspend fun getAllArabicSurah(): QuranBaseResponse {

        val jsonString =
            if (Prefs.contains(TEXT_QURAN).not()) {
                assets.readJsonStringFromAssets(arabicFile)?.also {
                    Prefs.putAny(TEXT_QURAN, it)
                }
            } else Prefs.getString(TEXT_QURAN, "")

        return Gson().fromJson(jsonString, QuranBaseResponse::class.java)
    }
}
package com.islam.hesn.myapplication.quran.model.repo.translation

import com.islam.hesn.myapplication.quran.model.response.translation.AyaTranslationItem

interface TranslationRepo {

    suspend fun getAya(translationKey: String, suraNum: Int, ayaNum: Int): AyaTranslationItem
}
package com.islam.hesn.myapplication.quran.model.repo.translation

import com.islam.hesn.myapplication.quran.model.response.translation.AyaTranslationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslationRepoImpl @Inject constructor(private val apis: QuranTranslationApis) :
    TranslationRepo {

    override suspend fun getAya(
        translationKey: String,
        suraNum: Int,
        ayaNum: Int
    ): AyaTranslationItem = withContext(Dispatchers.IO) {
        apis.getAyat(translationKey, suraNum, ayaNum)
    }
}
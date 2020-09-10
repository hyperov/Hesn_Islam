package com.islam.hesn.myapplication.quran.model.repo.arabic

import com.islam.hesn.myapplication.quran.model.response.arabic.QuranBaseResponse


interface QuranRepo {

    fun getAllArabicSurah(): QuranBaseResponse

}
package com.islam.hesn.myapplication.model.repo.arabic

import com.islam.hesn.myapplication.model.response.arabic.QuranBaseResponse


interface QuranRepo {

    fun getAllArabicSurah(): QuranBaseResponse

}
package com.islam.hesn.myapplication.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.islam.hesn.myapplication.model.repo.arabic.QuranRepo
import com.islam.hesn.myapplication.model.response.arabic.SurahItem

class QuranViewModel @ViewModelInject constructor(
    private val quranRepo: QuranRepo,
) : ViewModel() {

    val surahs = MutableLiveData<ArrayList<SurahItem>>()
    val ayat = MutableLiveData<ArrayList<SurahItem>>()
    val surahId = MutableLiveData<Int>()

    fun getAllArabicSurah() {
        val ayatList = quranRepo.getAllArabicSurah().list
        ayat.postValue(ayatList)
        surahs.postValue(ayatList.distinctBy { it.sura_id } as ArrayList<SurahItem>)

    }

}
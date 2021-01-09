package com.islam.hesn.myapplication.quran.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.quran.model.repo.arabic.QuranRepo
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class QuranViewModel @ViewModelInject constructor(
    private val quranRepo: QuranRepo,
) : ViewModel() {

    val surahs = MutableLiveData<ArrayList<AyaItem>>()
    val ayat = MutableLiveData<ArrayList<AyaItem>>()
    val surahId = MutableLiveData<Int>()
    val ayaFastForwardId = MutableLiveData<Int>()
    val isBookMark = MutableLiveData(false)

    val loading = MutableLiveData<Boolean>()

    fun getAllArabicSurah() {
        loading.value = true
        viewModelScope.launch {
            quranRepo.getAllArabicSurah().list.also { ayatList ->
                ayat.postValue(ayatList)
                surahs.postValue(ayatList.distinctBy { it.sura_id } as ArrayList<AyaItem>)
            }
        }

        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
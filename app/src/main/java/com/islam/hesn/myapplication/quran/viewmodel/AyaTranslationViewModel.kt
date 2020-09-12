package com.islam.hesn.myapplication.quran.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.quran.model.repo.translation.TranslationRepo
import com.islam.hesn.myapplication.quran.model.response.translation.Aya
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AyaTranslationViewModel @ViewModelInject constructor(private val repo: TranslationRepo) :
    ViewModel() {

    val ayaNum = MutableLiveData<Int>()
    val suraNum = MutableLiveData<Int>()

    val aya = MutableLiveData<Aya>()

    val loading = MutableLiveData<Boolean>()

    suspend fun getAyah(translationKey: String, suraNum: Int, ayaNum: Int) {
        loading.value = true
        viewModelScope.launch {
            aya.postValue(repo.getAya(translationKey, suraNum, ayaNum).aya)
            loading.postValue(false)
        }



    }




}
package com.islam.hesn.myapplication.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.model.repo.translation.TranslationRepo
import com.islam.hesn.myapplication.model.response.translation.Aya
import kotlinx.coroutines.launch

class AyaTranslationViewModel @ViewModelInject constructor(private val repo: TranslationRepo) :
    ViewModel() {

    val aya = MutableLiveData<Aya>()
    val ayaNum = MutableLiveData<Int>()
    val suraNum = MutableLiveData<Int>()

    fun getAyah(translationKey: String, suraNum: Int, ayaNum: Int) {
        viewModelScope.launch {
            aya.postValue(repo.getAya(translationKey, suraNum, ayaNum).aya)
        }
    }
}
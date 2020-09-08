package com.islam.hesn.myapplication.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.islam.hesn.myapplication.model.repo.translation.TranslationRepo
import com.islam.hesn.myapplication.model.response.arabic.SurahItem
import com.islam.hesn.myapplication.model.response.translation.AyaTranslationItem

class SurahViewModel @ViewModelInject constructor(
    private val translationRepo: TranslationRepo,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val surahs = MutableLiveData<ArrayList<SurahItem>>()



}
package com.islam.hesn.myapplication.bible.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BibleTranslationViewModel @Inject constructor(
    private val bibleRepo: BibleRepo,
) : ViewModel() {

    val verse = MutableLiveData<Verse>()
    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)

    val bookNum = MutableLiveData<Int>()
    val chapterNum = MutableLiveData<Int>()
    val verseNum = MutableLiveData<Int>()

    fun getVerseTranslation(translation: String) {

        viewModelScope.launch {

            bibleRepo.getTranslatedVerse(
                translation,
                bookNum.value!!.toString(),
                chapterNum.value!!.toString(),
                verseNum.value!!
            ).flowOn(Dispatchers.IO)
                .onStart {
                    loading.value = true
                    error.value = false
                }
                .catch { error.value = true }
                .onCompletion { loading.value = false }
                .collectLatest { verse.value = it }

        }

    }
}
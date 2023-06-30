package com.islam.hesn.myapplication.bible.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BibleTranslationViewModel @Inject constructor(
    private val bibleRepo: BibleRepo,
) : ViewModel() {

    val verse = MutableLiveData<Verse>()
    val loading = MutableLiveData(false)
    val error = MutableLiveData(false)

    val bookName = MutableLiveData<String>()
    val chapterNum = MutableLiveData<Int>()
    val verseNum = MutableLiveData<Int>()

    fun getVerseTranslation(translation: String) {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            try {
                verse.value =
                    bibleRepo.getTranslatedVerse(
                        translation,
                        "${bookName.value}${chapterNum.value}:${verseNum.value}"
                    ).book.first().verseMap.getValue(verseNum.value!!.toString())
            } catch (e: Exception) {
                error.value = true
            } finally {
                loading.value = false
                error.value = false
            }

        }

    }
}
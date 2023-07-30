package com.islam.hesn.myapplication.bible.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BibleViewModel @Inject constructor(
    private val bibleRepo: BibleRepo,
) : ViewModel() {

    val bookModels = MutableLiveData<List<Book>>()
    val chapterModels = MutableLiveData<List<Chapter>>()

    val verseModels = MutableLiveData<List<Verse>>()

    val selectedBook = MutableLiveData<Book>()
    val selectedChapter = MutableLiveData<Chapter>()
    val selectedFastForwardVerse = MutableLiveData<Int>(1)

    val selectedTitle = MutableLiveData<String>()

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData(false)
    val success = MutableLiveData(false)

    fun getBibleBooks(translation: String) {

        viewModelScope.launch {

            bibleRepo.getBibleBooks(translation)
                .flowOn(Dispatchers.IO)
                .onStart {
                    loading.value = true
                    error.value = false
                    success.value = false
                }.catch {
                    Log.e("getBible", "getBible: ${it.message}", it)
                    error.value = true
                    success.value = false
                }.onCompletion {
                    loading.value = false
                }.collect {
                    bookModels.value = it.values.toList()
                    success.value = true
                }

        }

    }

    fun getChaptersForSelectedBook(translation: String, bookNum: Int) {

        viewModelScope.launch {
            bibleRepo.getBibleBookChapters(translation, bookNum.toString())
                .flowOn(Dispatchers.IO)
                .onStart {
                    loading.value = true
                    error.value = false
                    success.value = false
                }.catch {
                    Log.e("getBible selected book", " ${it.message}", it)
                    error.value = true
                    success.value = false
                }.onCompletion {
                    loading.value = false
                }.collect {
                    chapterModels.value = it.chapters
                    success.value = true
                }
        }

    }

    fun getVersesForSelectedChapter() {
        verseModels.value = selectedChapter.value!!.verses
//        verseModels.value = chapterModels.value?.filter { it.chapterNum == selectedChapter.value }
//            ?.get(0)!!.verseMap.values.toList()
    }

    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
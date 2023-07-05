package com.islam.hesn.myapplication.bible.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.bible.BibleResponse
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
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
    val selectedChapter = MutableLiveData<Int>()
    val selectedFastForwardVerse = MutableLiveData<Int>(1)

    val selectedTitle = MutableLiveData<String>()

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData(false)
    val success = MutableLiveData(false)

    fun getBible(translation: String) {

        viewModelScope.launch {

            bibleRepo.getBible(translation)
                .flowOn(Dispatchers.IO)
                .onStart {
                    loading.value = true
                    error.value = false
                    success.value = false
                }.catch {
                    error.value = true
                    success.value = false
                }.onCompletion {
                    loading.value = false
                }.collect {
                    bookModels.value = it.booksMap.values.toList()
                    success.value = true
                }

        }

    }

    fun getChaptersForSelectedBook() {
        chapterModels.value =
            bookModels.value?.filter { it.bookNum == selectedBook.value!!.bookNum }
                ?.get(0)!!.chaptersMap.values.toList()
    }

    fun getVersesForSelectedChapter() {
        verseModels.value = chapterModels.value?.filter { it.chapterNum == selectedChapter.value }
            ?.get(0)!!.verseMap.values.toList()
    }

    public override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
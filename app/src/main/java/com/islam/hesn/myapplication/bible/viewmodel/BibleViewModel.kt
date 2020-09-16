package com.islam.hesn.myapplication.bible.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import kotlinx.coroutines.launch

class BibleViewModel @ViewModelInject constructor(
    private val bibleRepo: BibleRepo,
) : ViewModel() {

    val bookModels = MutableLiveData<List<Book>>()
    val chapterModels = MutableLiveData<List<Chapter>>()

    val verseModels = MutableLiveData<List<Verse>>()

    val selectedBook = MutableLiveData<Book>()
    val selectedChapter = MutableLiveData<Int>()

    fun getBible(translation: String) {
        viewModelScope.launch {

            val bookValues = bibleRepo.getBible(translation).booksMap.values.toList()
            bookModels.value = bookValues

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
}
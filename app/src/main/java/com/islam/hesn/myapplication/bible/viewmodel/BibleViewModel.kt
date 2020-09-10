package com.islam.hesn.myapplication.bible.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.Book
import com.islam.hesn.myapplication.bible.model.response.Chapter
import com.islam.hesn.myapplication.bible.model.response.Verse
import kotlinx.coroutines.launch

class BibleViewModel @ViewModelInject constructor(
    private val bibleRepo: BibleRepo,
) : ViewModel() {

    val bookModels = MutableLiveData<List<Book>>()

    private val chapterModels = MutableLiveData<List<Chapter>>()
    val chapters = MutableLiveData<List<Int>>()

    val verseModels = MutableLiveData<List<Verse>>()

    val selectedBook = MutableLiveData<String>()
    val selectedChapter = MutableLiveData<Int>()
    val selectedVerse = MutableLiveData<String>()

    fun getBible(translation: String) {
        viewModelScope.launch {

            val bookValues = bibleRepo.getBible(translation).booksMap.values.toList()
            bookModels.value = bookValues

        }

    }

    fun getChaptersForSelectedBook() {

        val chapterValues =
            bookModels.value?.filter { it.bookName.equals(selectedBook.value, false) }
                ?.get(0)!!.chaptersMap.values
        chapterModels.value = chapterValues as List<Chapter>

        val chapterNames = chapterValues.map { it.chapterNum }
        chapters.value = chapterNames
    }

    fun getVersesForSelectedChapter() {
        val verseValues = chapterModels.value?.filter { it.chapterNum == selectedChapter.value }
            ?.get(0)!!.verseMap.values as List<Verse>
        verseModels.value = verseValues
    }
}
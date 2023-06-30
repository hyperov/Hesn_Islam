package com.islam.hesn.myapplication.bible.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islam.hesn.myapplication.bible.model.repo.BibleRepo
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.cancel
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

    val selectedTitle = MutableLiveData<String>()

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData(false)
    val success = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()

    fun getBible(translation: String) {
        loading.value = true
        error.value = false
        success.value = false

        val disposable = bibleRepo.getBible(translation).subscribeOn(Schedulers.io()).cache()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    bookModels.value = it.booksMap.values.toList()
                    success.value = true
                },
                {
                    error.value = true
                    success.value = false
                }, { loading.value = false }
            )
        compositeDisposable.add(disposable)

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
        compositeDisposable.clear()
    }
}
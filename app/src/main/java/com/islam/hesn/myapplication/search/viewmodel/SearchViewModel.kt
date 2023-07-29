package com.islam.hesn.myapplication.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.islam.hesn.myapplication.search.model.SearchedVerse
import com.islam.hesn.myapplication.search.model.Section


class SearchViewModel : ViewModel() {

    val searchQuery = MutableLiveData<String>()
    val isFromQuranScreen = MutableLiveData<Boolean>()
    val ayat = MutableLiveData<ArrayList<AyaItem>>()
    val searchedAyatSections = MutableLiveData<ArrayList<Section<AyaItem>>>()

    private val sectionsQuranList = arrayListOf<Section<AyaItem>>()
    private val sectionsBibleList = arrayListOf<Section<Verse>>()

    val booksBible = MutableLiveData<List<Book>>()
    private val searchVerses = ArrayList<SearchedVerse>()

    val searchedVersesLiveData = MutableLiveData<ArrayList<SearchedVerse>>()

    val searchedVersesSections = MutableLiveData<ArrayList<Section<Verse>>>()

    val emptySearch = MutableLiveData(false)
    val emptySearchText = MutableLiveData(false)


    fun getQuranValues() {

        emptySearch.value = true
        emptySearchText.value = false

        val ayatContainingSearchQuery =
            ayat.value?.filter { it.standard.contains(searchQuery.value!!, true) }

        val ayatMap = ayatContainingSearchQuery?.groupBy { it.sura_name }

        sectionsQuranList.clear()
        ayatMap?.forEach { entry -> sectionsQuranList.add(Section(entry.key, entry.value)) }
        if (sectionsQuranList.isNotEmpty()) {
            emptySearch.value = false
            searchedAyatSections.value = sectionsQuranList
        } else
            emptySearchText.value = true
    }

    fun getBibleValues(bookTitlesArabic: List<String>) {

        searchVerses.clear()
        sectionsBibleList.clear()
        emptySearch.value = true
        emptySearchText.value = false

        booksBible.value?.forEachIndexed { index, book ->
//            book.chaptersMap.values.forEach { chapter ->
//                val filteredVerses = chapter.verseMap.values.filter {
//                    it.verseContent.contains(
//                        searchQuery.value!!, true
//                    )
//                }
//                if (filteredVerses.isNotEmpty()) {
//
//                    searchVerses.add(SearchedVerse(bookTitlesArabic[index],
//                        chapter.chapterNum.toString(),
//                        filteredVerses, book.bookName))
//                }
//            }

        }
        if (searchVerses.isNotEmpty()) {
            emptySearch.value = false
            searchedVersesLiveData.value = searchVerses

            searchVerses.forEach {
                sectionsBibleList.add(Section(" ${it.bookName}   ${it.chapterName}",
                    it.verseList))
            }
            searchedVersesSections.value = sectionsBibleList
        } else
            emptySearchText.value = true
    }

}
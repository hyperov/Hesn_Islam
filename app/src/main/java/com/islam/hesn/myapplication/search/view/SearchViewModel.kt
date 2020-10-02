package com.islam.hesn.myapplication.search.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.islam.hesn.myapplication.quran.model.response.arabic.SurahItem
import com.islam.hesn.myapplication.search.model.Section


class SearchViewModel : ViewModel() {

    val searchQuery = MutableLiveData<String>()
    val isFromQuranScreen = MutableLiveData<Boolean>()
    val ayat = MutableLiveData<ArrayList<SurahItem>>()
    val searchedAyatSections = MutableLiveData<ArrayList<Section<SurahItem>>>()

    private val sectionsQuranList = arrayListOf<Section<SurahItem>>()

    fun getQuranValues() {

        val ayatContainingSearchQuery =
            ayat.value?.filter { it.standard.contains(searchQuery.value!!, true) }

        val ayatMap = ayatContainingSearchQuery?.groupBy { it.sura_name }

        sectionsQuranList.clear()
        ayatMap?.forEach { entry -> sectionsQuranList.add(Section(entry.key, entry.value)) }

        searchedAyatSections.value = sectionsQuranList
    }

}
package com.islam.hesn.myapplication.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.search.model.SearchExpandableAdapter
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData()
        getQuranSearchValues()
    }

    private fun getQuranSearchValues() {
        searchViewModel.getQuranValues()
    }

    private fun observeData() {
        searchViewModel.searchedAyatSections.observe(viewLifecycleOwner, { sections ->
            when (searchViewModel.isFromQuranScreen.value) {
                true -> {
                    list.adapter = SearchExpandableAdapter(sections)
                }
                false -> {
//                    list.adapter = SearchExpandableAdapter<Verse>()
                }
            }
        })
    }

}
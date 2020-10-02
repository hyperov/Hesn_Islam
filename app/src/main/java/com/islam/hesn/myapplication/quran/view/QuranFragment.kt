package com.islam.hesn.myapplication.quran.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH_LIST
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.search.view.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*


const val SEARCH_QUERY = "SEARCH_QUERY"

@AndroidEntryPoint
class QuranFragment : Fragment(), TextView.OnEditorActionListener {

    private lateinit var searchView: SearchView
    private val quranViewModel: QuranViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_quran_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        getSurahs()
        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {
        etSearch.setOnTouchListener(OnTouchListener { v, event ->

            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= etSearch.right - etSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    // your action here
                    gotoSearchScreen(etSearch.text.toString())
                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun getSurahs() {
        quranViewModel.getAllArabicSurah()
    }

    private fun observeData() {
        quranViewModel.surahs.observe(viewLifecycleOwner, {
            list.adapter =
                MySurahRecyclerViewAdapter(it, QURAN_SURAH_LIST, { surahId ->
                    if (quranViewModel.surahId.value != surahId)
                        quranViewModel.surahId.value = surahId
                    findNavController().navigate(R.id.surahFragment)
                })
        })
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            gotoSearchScreen(etSearch.text.toString())
        }
        return true

    }

    private fun gotoSearchScreen(searchText: String) {
        searchViewModel.searchQuery.value = searchText
        searchViewModel.isFromQuranScreen.value = true
        searchViewModel.ayat.postValue(quranViewModel.ayat.value)
        findNavController().navigate(R.id.searchFragment)
    }

}
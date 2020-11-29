package com.islam.hesn.myapplication.search.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.search.model.SearchExpandableAdapter
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener
import kotlinx.android.synthetic.main.fragment_bible_list.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.etSearch
import kotlinx.android.synthetic.main.search_fragment.searchList

class SearchFragment : Fragment(), TextView.OnEditorActionListener, OnGroupClickListener {

    private val searchViewModel: SearchViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData()
        getQuranOrBibleSearchValues()
        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
    }

    private fun getQuranOrBibleSearchValues() {
        when (searchViewModel.isFromQuranScreen.value) {
            true -> {
                searchViewModel.getQuranValues()
            }
            false -> {
                searchViewModel.getBibleValues(resources.getStringArray(R.array.bible_books)
                    .toList())
            }
        }

    }

    private fun observeData() {
        searchViewModel.searchedAyatSections.observe(viewLifecycleOwner, { sections ->
            if (searchViewModel.isFromQuranScreen.value!!) {
                searchList.adapter = SearchExpandableAdapter(sections, true).also {
                    it.setOnGroupClickListener(this@SearchFragment)
                }
            }
        })
        searchViewModel.searchedVersesSections.observe(viewLifecycleOwner, { sections ->
            if (!searchViewModel.isFromQuranScreen.value!!) {
                searchList.adapter = SearchExpandableAdapter(sections, false)
            }
        })
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (etSearch.text.isNullOrEmpty().not() and (etSearch.text!!.isNotBlank()))
                createNewSearchQuery(etSearch.text.toString())
        }
        return true
    }

    private fun setSearchTypingListener() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.toString().isNullOrBlank()) {
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_search,
                        0
                    )
                } else if (s?.toString()?.isNotBlank()!! && s.toString().isNotEmpty()) {
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        android.R.drawable.ic_menu_close_clear_cancel,
                        0,
                        R.drawable.ic_search,
                        0
                    )
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {
        etSearch.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                etSearch.compoundDrawables[DRAWABLE_RIGHT]?.let {
                    if (event.rawX >= etSearch.right - it.bounds.width()) {
                        if (etSearch.text.isNullOrEmpty().not() and (etSearch.text!!.isNotBlank()))
                            createNewSearchQuery(etSearch.text.toString())
                        return@OnTouchListener true
                    }
                }
                etSearch.compoundDrawables[DRAWABLE_LEFT]?.let {
                    if (event.rawX <= it.bounds.width() + 2 * etSearch.paddingLeft) {
                        etSearch.editableText.clear()
                        return@OnTouchListener true
                    }
                }
            }
            false
        })
    }

    private fun createNewSearchQuery(searchText: String) {
        searchViewModel.searchQuery.value = searchText
        getQuranOrBibleSearchValues()
    }

    override fun onGroupClick(flatPos: Int): Boolean {
        return false
    }


}
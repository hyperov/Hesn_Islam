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
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.search.model.SearchExpandableAdapter
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment(), TextView.OnEditorActionListener {

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
        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
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
                    if (event.rawX >= etSearch.left - it.bounds.width()) {
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
        getQuranSearchValues()
    }

}
package com.islam.hesn.myapplication.bible.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum.BOOKS
import com.islam.hesn.myapplication.bible.view.BibleLangEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*

@AndroidEntryPoint
class BibleFragment : Fragment(), TextView.OnEditorActionListener {

    private val bibleViewModel: BibleViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bible_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        getBooks()
        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
    }

    private fun getBooks() {
        bibleViewModel.getBible(BibleLangEnum.VAN_DYKE.lang)
    }

    private fun observeData() {
        bibleViewModel.bookModels.observe(viewLifecycleOwner, {
            list.adapter =
                BibleMainRecyclerViewAdapter(
                    books = it!!,
                    state = BOOKS,
                    onBookItemClick = { bookNum ->
                        bibleViewModel.selectedBook.value = bookNum
                        findNavController().navigate(R.id.bookFragment)
                    })
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {
        etSearch.setOnTouchListener { v, event ->

            val DRAWABLE_LEFT = 0
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                etSearch.compoundDrawables[DRAWABLE_RIGHT]?.let {
                    if (event.rawX >= etSearch.right - etSearch.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {

                        gotoSearchScreen(etSearch.text.toString())
                        return@setOnTouchListener true
                    }
                }
                etSearch.compoundDrawables[DRAWABLE_LEFT]?.let {
                    if (event.rawX <= it.bounds.width() + 2 * etSearch.paddingLeft) {
                        etSearch.editableText.clear()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            gotoSearchScreen(etSearch.text.toString())
        }
        return true
    }

    private fun gotoSearchScreen(searchText: String) {
        searchViewModel.searchQuery.value = searchText
        searchViewModel.isFromQuranScreen.value = false
        findNavController().navigate(R.id.searchFragment)
    }

}
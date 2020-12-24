@file:Suppress("LocalVariableName", "LocalVariableName", "LocalVariableName", "LocalVariableName")

package com.islam.hesn.myapplication.bible.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum.BOOKS
import com.islam.hesn.myapplication.bible.view.BibleLangEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bible_list.*
import kotlinx.android.synthetic.main.fragment_quran_list.etSearch
import kotlinx.android.synthetic.main.fragment_quran_list.fabJump
import kotlinx.android.synthetic.main.layout_dialog_bible_fast_navigation.*
import kotlinx.android.synthetic.main.layout_dialog_surah_fast_navigation.btFastForwardDone

@AndroidEntryPoint
class BibleFragment : Fragment(), TextView.OnEditorActionListener {

    private val bibleViewModel: BibleViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    lateinit var selectedBook: Book
    lateinit var selectedChapter: Chapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_bible_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListDivider()
        observeData()
        getBooks()
        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()

        fabJump.setOnClickListener {
            fabJump.isExpanded = !fabJump.isExpanded
        }

        btFastForwardDone.setOnClickListener {

            fabJump.isExpanded = !fabJump.isExpanded
            bibleViewModel.selectedBook.value = selectedBook
            bibleViewModel.selectedChapter.value = selectedChapter.chapterNum
            findNavController().navigate(R.id.chapterFragment)
        }

        btCancel.setOnClickListener { fabJump.isExpanded = !fabJump.isExpanded }
    }

    private fun setListDivider() {
        searchList.addItemDecoration(DividerItemDecoration(context,
            DividerItemDecoration.VERTICAL))
    }

    private fun getBooks() {
        bibleViewModel.getBible(BibleLangEnum.VAN_DYKE.lang)
    }

    private fun observeData() {
        bibleViewModel.bookModels.observe(viewLifecycleOwner, {

            searchList.adapter =
                BibleMainRecyclerViewAdapter(
                    books = it!!,
                    state = BOOKS,
                    onBookItemClick = { book, title ->

                        bibleViewModel.selectedBook.value = book
                        bibleViewModel.selectedTitle.value = title
                        findNavController().navigate(R.id.bookFragment)
                    }, isVerse = false)
            setupFastForwardSpinnerAdapter(it)
        })

        bibleViewModel.loading.observe(viewLifecycleOwner, { isVisible ->

            if (isVisible) {
                progressBible.visibility = View.VISIBLE
                progressBible.playAnimation()
            } else {
                progressBible.visibility = View.GONE
                progressBible.cancelAnimation()
            }

        })

        bibleViewModel.error.observe(viewLifecycleOwner, { isError ->

            if (isError) {
                fabJump.hide()
                etSearch.isEnabled = false
                searchList.visibility = View.GONE
                progressBible.visibility = View.GONE
                errorBible.visibility = View.VISIBLE
                errorTextBible.visibility = View.VISIBLE
            }
        })

        bibleViewModel.success.observe(viewLifecycleOwner, { isSuccess ->

            if (isSuccess) {
                fabJump.show()
                searchList.visibility = View.VISIBLE
                etSearch.isEnabled = true
            } else {
                fabJump.hide()
                searchList.visibility = View.GONE
                etSearch.isEnabled = false
            }
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
                        if (etSearch.text!!.isNotEmpty())
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
            if (etSearch.text!!.isNotEmpty())
                gotoSearchScreen(etSearch.text.toString())
        }
        return true
    }

    private fun gotoSearchScreen(searchText: String) {
        searchViewModel.searchQuery.value = searchText
        searchViewModel.isFromQuranScreen.value = false
        searchViewModel.booksBible.postValue(bibleViewModel.bookModels.value)
        findNavController().navigate(R.id.searchFragment)
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

    private fun setupFastForwardSpinnerAdapter(books: List<Book>) {

        lateinit var chapters: List<Chapter>
        lateinit var verses: List<Verse>

        val arabicTitles = resources.getStringArray(R.array.bible_books_dialog)
        setupSpinnerArrayAdapter(arabicTitles.toList(), spinnerBook)

        spinnerBook.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedBook = books[position]
                chapters = selectedBook.chaptersMap.values.toList()
                setupSpinnerArrayAdapter(chapters.map { it.chapterNum }, spinnerChapter)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedChapter = chapters[position]
                verses = selectedChapter.verseMap.values.toList()
                setupSpinnerArrayAdapter(verses.map { it.verseNum }, spinnerVerse)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSpinnerArrayAdapter(books: List<Any>, spinner: Spinner) {
        ArrayAdapter(
            requireContext(),
            R.layout.layout_spinner_drop_down_resource,
            books

        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.layout_spinner_drop_down_resource)
            spinner.adapter = adapter
        }
    }

}
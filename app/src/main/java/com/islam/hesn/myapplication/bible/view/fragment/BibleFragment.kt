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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum.BOOKS
import com.islam.hesn.myapplication.bible.view.BibleLangEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.databinding.FragmentBibleListBinding
import com.islam.hesn.myapplication.databinding.LayoutDialogBibleFastNavigationBinding
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import com.islam.hesn.myapplication.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BibleFragment : Fragment(), TextView.OnEditorActionListener {


    //R.layout.fragment_bible_list
    private var _binding: FragmentBibleListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialogBinding: LayoutDialogBibleFastNavigationBinding

    private val bibleViewModel: BibleViewModel by activityViewModels()

    private val searchViewModel: SearchViewModel by activityViewModels()

    lateinit var selectedBook: Book
    lateinit var selectedChapter: Chapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentBibleListBinding.inflate(inflater, container, false)
        dialogBinding = binding.layoutDialogBibleFastNavigation

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "BibleFragment")
        setListDivider()
        observeData()
        getBooks()
        binding.etSearchBible.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
        setRefreshListener()

        val fabJump = binding.fabJump
        fabJump.setOnClickListener {
            fabJump.isExpanded = !fabJump.isExpanded
        }

        dialogBinding.btFastForwardDone.setOnClickListener {

            fabJump.isExpanded = !fabJump.isExpanded
            bibleViewModel.selectedBook.value = selectedBook
            bibleViewModel.selectedChapter.value = selectedChapter.chapterNum
            bibleViewModel.selectedFastForwardVerse.value =
                dialogBinding.spinnerVerse.selectedItemPosition + 1
            findNavController().navigate(R.id.chapterFragment)
            Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
        }

        dialogBinding.btCancel.setOnClickListener { fabJump.isExpanded = !fabJump.isExpanded }
    }

    private fun setListDivider() {
        binding.searchList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setRefreshListener() {
        binding.refreshBible.setOnRefreshListener {
            getBooks()
        }
    }

    private fun getBooks() {
        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

            val bottomNavView: BottomNavigationView =
                activity?.findViewById(R.id.bottomNavigation)!!

            requireContext().showSnackBar(
                binding.searchList,
                bottomNavView,
                getString(R.string.error_no_connection),
                android.R.color.holo_red_light
            )

        }
        if (bibleViewModel.bookModels.value.isNullOrEmpty())
            bibleViewModel.getBible(BibleLangEnum.VAN_DYKE.lang)
        FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "BIBLE")
    }

    private fun observeData() {

        bibleViewModel.bookModels.observe(viewLifecycleOwner) {

            binding.searchList.adapter =
                BibleMainRecyclerViewAdapter(
                    books = it!!,
                    state = BOOKS,
                    onBookItemClick = { book, title ->

                        bibleViewModel.selectedBook.value = book
                        bibleViewModel.selectedTitle.value = title
                        findNavController().navigate(R.id.bookFragment)
                    }, isVerse = false
                )
            setupFastForwardSpinnerAdapter(it)
        }

        bibleViewModel.loading.observe(viewLifecycleOwner) { isVisible ->

            val progressBible = binding.progressBible
            if (isVisible) {
                progressBible.visibility = View.VISIBLE
                progressBible.playAnimation()
            } else {
                progressBible.visibility = View.GONE
                progressBible.cancelAnimation()
                binding.refreshBible.isRefreshing = false
            }

        }

        bibleViewModel.error.observe(viewLifecycleOwner) { isError ->

            val errorBible = binding.errorBible
            val errorTextBible = binding.errorTextBible
            if (isError) {
                errorBible.visibility = View.VISIBLE
                errorTextBible.visibility = View.VISIBLE
            } else {
                errorBible.visibility = View.GONE
                errorTextBible.visibility = View.GONE
            }
        }

        bibleViewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            binding.apply {
                if (isSuccess) {
                    fabJump.show()
                    searchList.visibility = View.VISIBLE
                    etSearchBible.isEnabled = true
                } else {
                    fabJump.hide()
                    searchList.visibility = View.GONE
                    etSearchBible.isEnabled = false
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {

        binding.apply {
            etSearchBible.setOnTouchListener { _, event ->

                val DRAWABLE_LEFT = 0
                val DRAWABLE_RIGHT = 2

                if (event.action == MotionEvent.ACTION_UP) {
                    etSearchBible.compoundDrawables[DRAWABLE_RIGHT]?.let {
                        if (event.rawX >= etSearchBible.right - etSearchBible.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                            if (etSearchBible.text!!.isNotEmpty()) {
                                if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                                    val bottomNavView: BottomNavigationView =
                                        activity?.findViewById(R.id.bottomNavigation)!!

                                    requireContext().showSnackBar(
                                        searchList,
                                        bottomNavView,
                                        getString(R.string.error_no_connection),
                                        android.R.color.holo_red_light
                                    )

                                    return@let
                                } else
                                    gotoSearchScreen(etSearchBible.text.toString())
                            }
                            return@setOnTouchListener true
                        }
                    }
                    etSearchBible.compoundDrawables[DRAWABLE_LEFT]?.let {
                        if (event.rawX <= it.bounds.width() + 2 * etSearchBible.paddingLeft) {
                            etSearchBible.editableText.clear()
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        binding.apply {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                etSearchBible.let {
                    if (etSearchBible.text!!.isNotEmpty()) {
                        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                            val bottomNavView: BottomNavigationView =
                                activity?.findViewById(R.id.bottomNavigation)!!

                            requireContext().showSnackBar(
                                searchList,
                                bottomNavView,
                                getString(R.string.error_no_connection),
                                android.R.color.holo_red_light
                            )

                            return false
                        } else
                            gotoSearchScreen(etSearchBible.text.toString())
                    }
                }
            }
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

        binding.apply {

            etSearchBible.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    etSearchBible.let {
                        if (s?.toString().isNullOrBlank()) {
                            etSearchBible.setCompoundDrawablesWithIntrinsicBounds(
                                0,
                                0,
                                R.drawable.ic_search,
                                0
                            )
                        } else if (s?.toString()?.isNotBlank()!! && s.toString().isNotEmpty()) {
                            etSearchBible.setCompoundDrawablesWithIntrinsicBounds(
                                android.R.drawable.ic_menu_close_clear_cancel,
                                0,
                                R.drawable.ic_search,
                                0
                            )
                        }
                    }
                }
            })
        }
    }

    private fun setupFastForwardSpinnerAdapter(books: List<Book>) {

        lateinit var chapters: List<Chapter>
        lateinit var verses: List<Verse>

        val arabicTitles = resources.getStringArray(R.array.bible_books_dialog)
        setupSpinnerArrayAdapter(arabicTitles.toList(), dialogBinding.spinnerBook)

        dialogBinding.spinnerBook.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {


                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectedBook = books[position]
                    chapters = selectedBook.chaptersMap.values.toList()
                    setupSpinnerArrayAdapter(
                        chapters.map { it.chapterNum },
                        dialogBinding.spinnerChapter
                    )

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        dialogBinding.spinnerChapter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    selectedChapter = chapters[position]
                    verses = selectedChapter.verseMap.values.toList()
                    setupSpinnerArrayAdapter(verses.map { it.verseNum }, dialogBinding.spinnerVerse)

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

    override fun onDestroy() {
        super.onDestroy()
        bibleViewModel.selectedFastForwardVerse.value = 1
        findNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
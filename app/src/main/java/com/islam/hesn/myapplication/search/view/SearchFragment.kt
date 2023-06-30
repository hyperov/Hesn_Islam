package com.islam.hesn.myapplication.search.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.fragment.TranslationBibleBottomSheetFragment
import com.islam.hesn.myapplication.bible.viewmodel.BibleTranslationViewModel
import com.islam.hesn.myapplication.databinding.SearchFragmentBinding
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.islam.hesn.myapplication.quran.view.TranslationQuranBottomSheetFragment
import com.islam.hesn.myapplication.quran.viewmodel.AyaTranslationViewModel
import com.islam.hesn.myapplication.search.model.SearchExpandableAdapter
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import com.islam.hesn.myapplication.utils.*
import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), TextView.OnEditorActionListener, OnGroupClickListener {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val ayaViewModel: AyaTranslationViewModel by activityViewModels()
    private val bibleTranslationViewModel: BibleTranslationViewModel by activityViewModels()
    private lateinit var bottomSheetQuran: TranslationQuranBottomSheetFragment
    private lateinit var bottomSheetBible: TranslationBibleBottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "SearchFragment")
        Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
        observeData()
        observeTranslationData()
        getQuranOrBibleSearchValues()
        binding.etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
    }

    private fun observeTranslationData() {
        ayaViewModel.aya.observe(viewLifecycleOwner) { aya ->
            aya?.let {
                createDialog(aya.aya, aya.translation)
                ayaViewModel.aya.value = null
                Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
            }

        }

        ayaViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.progressSearch.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        ayaViewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val bottomNavView: BottomNavigationView =
                    activity?.findViewById(R.id.bottomNavigation)!!
                requireContext().showSnackBar(
                    binding.searchLayout,
                    bottomNavView,
                    getString(R.string.error_bible_quran_translation_api),
                    android.R.color.holo_red_light
                )
            }
        }

        bibleTranslationViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.progressSearch.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        bibleTranslationViewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val bottomNavView: BottomNavigationView =
                    activity?.findViewById(R.id.bottomNavigation)!!
                requireContext().showSnackBar(
                    binding.searchLayout,
                    bottomNavView,
                    getString(R.string.error_bible_quran_translation_api),
                    android.R.color.holo_red_light
                )
            }
        }

        bibleTranslationViewModel.verse.observe(viewLifecycleOwner) { verse ->
            verse?.let {

                createDialog(verse.verseNum.toString(), verse.verseContent)
                bibleTranslationViewModel.verse.value = null
                Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
            }
        }
    }

    private fun getQuranOrBibleSearchValues() {
        when (searchViewModel.isFromQuranScreen.value!!) {
            true -> {
                searchViewModel.getQuranValues()
                FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "QURAN_SEARCH")
            }
            false -> {
                searchViewModel.getBibleValues(
                    resources.getStringArray(R.array.bible_books)
                        .toList()
                )
                FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "BIBLE_SEARCH")
            }
        }

    }

    private fun observeData() {
        observeSearchQuranData()
        observeSearchBibleData()
    }

    private fun observeSearchBibleData() {
        searchViewModel.searchedVersesSections.observe(viewLifecycleOwner) { sections ->
            if (!searchViewModel.isFromQuranScreen.value!!) {
                binding.searchList.adapter =
                    SearchExpandableAdapter(sections, false, { verse: Verse ->

                        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                            val bottomNavView: BottomNavigationView =
                                activity?.findViewById(R.id.bottomNavigation)!!

                            requireContext().showSnackBar(
                                binding.searchLayout,
                                bottomNavView,
                                getString(R.string.error_no_connection),
                                android.R.color.holo_red_light
                            )

                            return@SearchExpandableAdapter
                        }

                        bibleTranslationViewModel.apply {

                            val searchedVerse =
                                searchViewModel.searchedVersesLiveData.value?.first {
                                    it.verseList.contains(verse)
                                }
                            bookName.value =
                                searchedVerse?.bookNameEn

                            chapterNum.value =
                                searchedVerse?.chapterName?.toInt()

                            verseNum.value = verse.verseNum
                        }

                        bottomSheetBible = TranslationBibleBottomSheetFragment.newInstance().apply {
                            showNow(this@SearchFragment.parentFragmentManager, "translation")
                        }
                    })
            }
        }
        searchViewModel.emptySearch.observe(
            viewLifecycleOwner
        ) { visible ->
            binding.run {
                noResultsSearch.isVisible = visible
                searchList.isVisible = !visible
                cvSearchFrag.isVisible = !visible
            }
        }
        searchViewModel.emptySearchText.observe(viewLifecycleOwner,
            Observer { visible -> binding.tvNoResults.isVisible = visible })
    }

    private fun observeSearchQuranData() {
        searchViewModel.searchedAyatSections.observe(viewLifecycleOwner) { sections ->
            if (searchViewModel.isFromQuranScreen.value!!) {
                binding.searchList.adapter = SearchExpandableAdapter(sections,
                    true,
                    searchAyaItemClick = { verse: AyaItem ->

                        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                            val bottomNavView: BottomNavigationView =
                                activity?.findViewById(R.id.bottomNavigation)!!

                            requireContext().showSnackBar(
                                binding.searchLayout,
                                bottomNavView,
                                getString(R.string.error_no_connection),
                                android.R.color.holo_red_light
                            )

                            return@SearchExpandableAdapter
                        }

                        ayaViewModel.ayaNum.value = verse.aya_id
                        ayaViewModel.suraNum.value = verse.sura_id
                        bottomSheetQuran = TranslationQuranBottomSheetFragment.newInstance().apply {

                            showNow(this@SearchFragment.parentFragmentManager, "translation")
                        }
                    }, onLastReadClick = { surahId: Int, ayaId: Int, surahName: String ->


                        Prefs.putAny(BOOKMARK_SURAH_NUMBER, surahId)
                        Prefs.putAny(BOOKMARK_AYA_NUMBER, ayaId)
                        Prefs.putAny(BOOKMARK_SURAH_NAME, surahName)
                        val bottomNavView: BottomNavigationView =
                            activity?.findViewById(R.id.bottomNavigation)!!
                        requireContext().showSnackBar(
                            binding.searchLayout, bottomNavView,
                            getString(R.string.bookmark_saved_successfully)
                        )
                        Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
                    }).also {
                    it.setOnGroupClickListener(this@SearchFragment)
                }
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        binding.apply {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (etSearch.text.isNullOrEmpty().not() and (etSearch.text!!.isNotBlank()))
                    createNewSearchQuery(etSearch.text.toString())
            }
            return true
        }
    }

    private fun setSearchTypingListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.toString().isNullOrBlank()) {
                    binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_search,
                        0
                    )
                } else if (s?.toString()?.isNotBlank()!! && s.toString().isNotEmpty()) {
                    binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(
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
        binding.apply {
            etSearch.setOnTouchListener(View.OnTouchListener { _, event ->
                val DRAWABLE_LEFT = 0
                val DRAWABLE_RIGHT = 2

                if (event.action == MotionEvent.ACTION_UP) {
                    etSearch.compoundDrawables[DRAWABLE_RIGHT]?.let {
                        if (event.rawX >= etSearch.right - it.bounds.width()) {
                            if (etSearch.text.isNullOrEmpty()
                                    .not() and (etSearch.text!!.isNotBlank())
                            )
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
    }

    private fun createNewSearchQuery(searchText: String) {
        searchViewModel.searchQuery.value = searchText
        getQuranOrBibleSearchValues()
    }

    override fun onGroupClick(flatPos: Int): Boolean {
        return false
    }


}
package com.islam.hesn.myapplication.bible.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleTranslationViewModel
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.databinding.FragmentChapterBinding
import com.islam.hesn.myapplication.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChapterFragment : Fragment() {

    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!

    private val bibleViewModel: BibleViewModel by activityViewModels()
    private val translationViewModel: BibleTranslationViewModel by activityViewModels()

    private lateinit var bottomSheet: TranslationBibleBottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentChapterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "ChapterFragment")
        changeToolbarTitle(bibleViewModel.selectedChapter.value!!.chapterNum.toString())
        binding.fab.setOnClickListener { binding.searchList.smoothScrollToPosition(0) }
        setListDivider()
        observeData()
        observeTranslationData()
        getVerses()
    }

    private fun setListDivider() {
        binding.searchList.addItemDecoration(DividerItemDecoration(context,
            DividerItemDecoration.VERTICAL))
    }

    private fun observeTranslationData() {
        translationViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.progressChapter.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        translationViewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val bottomNavView: BottomNavigationView =
                    activity?.findViewById(R.id.bottomNavigation)!!
                requireContext().showSnackBar(
                    requireActivity().findViewById(android.R.id.content),
                    bottomNavView,
                    getString(R.string.error_bible_quran_translation_api),
                    android.R.color.holo_red_light
                )
            }
        }

        translationViewModel.verse.observe(viewLifecycleOwner) { verse ->

            verse?.let {
                createBottomSheet(verse.verseNum.toString(), verse.verseContent,false)
                translationViewModel.verse.value = null
                Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
            }
        }
    }

    private fun getVerses() {
        if (bibleViewModel.chapterModels.value == null)
            bibleViewModel.getChaptersForSelectedBook(
                bibleViewModel.translationName.value!!,
                bibleViewModel.selectedBook.value!!.bookNum
            )
        bibleViewModel.getVersesForSelectedChapter()
    }

    private fun observeData() {
        bibleViewModel.verseModels.observe(viewLifecycleOwner) {
            binding.searchList.adapter =
                BibleMainRecyclerViewAdapter(
                    verses = it,
                    state = AdapterStateBibleEnum.VERSES,
                    isVerse = true,
                    onVerseItemClick = { verse ->

                        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                            val bottomNavView: BottomNavigationView =
                                activity?.findViewById(R.id.bottomNavigation)!!

                            requireContext().showSnackBar(
                                binding.chapterTranslation,
                                bottomNavView,
                                getString(R.string.error_no_connection),
                                android.R.color.holo_red_light
                            )

                            return@BibleMainRecyclerViewAdapter
                        }

                        translationViewModel.apply {

                            bookNum.value =
                                bibleViewModel.selectedBook.value!!.bookNum

                            chapterNum.value =
                                bibleViewModel.selectedChapter.value!!.chapterNum

                            verseNum.value = verse.verseNum
                        }

                        bottomSheet = TranslationBibleBottomSheetFragment.newInstance().apply {
                            showNow(this@ChapterFragment.parentFragmentManager, "translation")
                        }
                    })
            binding.searchList.scrollToPosition(bibleViewModel.selectedFastForwardVerse.value!! - 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
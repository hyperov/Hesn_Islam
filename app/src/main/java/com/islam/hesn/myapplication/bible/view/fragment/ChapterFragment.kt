package com.islam.hesn.myapplication.bible.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.home.changeToolbarTitle
import com.islam.hesn.myapplication.quran.view.TranslationQuranBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*

@AndroidEntryPoint
class ChapterFragment : Fragment() {

    private val bibleViewModel: BibleViewModel by activityViewModels()

    private lateinit var bottomSheet: TranslationQuranBottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeToolbarTitle(bibleViewModel.selectedChapter.value.toString())
        observeData()
        getVerses()
    }

    private fun getVerses() {
        bibleViewModel.getVersesForSelectedChapter()
    }

    private fun observeData() {
        bibleViewModel.verseModels.observe(viewLifecycleOwner, {
            list.adapter =
                BibleMainRecyclerViewAdapter(
                    verses = it,
                    state = AdapterStateBibleEnum.VERSES,
                    onVerseItemClick = { verseNum ->
                        bibleViewModel.selectedVerse.value = verseNum.toString()
                        bottomSheet = TranslationQuranBottomSheetFragment.newInstance().apply {

                            showNow(this@ChapterFragment.parentFragmentManager, "translation")
                        }
                    })
        })
    }

}
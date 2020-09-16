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
import com.islam.hesn.myapplication.bible.viewmodel.BibleTranslationViewModel
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.home.changeToolbarTitle
import com.islam.hesn.myapplication.home.createDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*
import kotlinx.android.synthetic.main.fragment_surah.*

@AndroidEntryPoint
class ChapterFragment : Fragment() {

    private val bibleViewModel: BibleViewModel by activityViewModels()
    private val translationViewModel: BibleTranslationViewModel by activityViewModels()

    private lateinit var bottomSheet: TranslationBibleBottomSheetFragment

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
        observeTranslationData()
        getVerses()
    }

    private fun observeTranslationData() {
        translationViewModel.loading.observe(viewLifecycleOwner, { isVisible ->
            progress.visibility = if (isVisible) View.VISIBLE else View.GONE
        })

        translationViewModel.verse.observe(viewLifecycleOwner, { verse ->
            verse?.let {

                createDialog(verse.verseNum.toString(), verse.verseContent)
                translationViewModel.verse.value = null
            }
        })
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
                    onVerseItemClick = { verse ->
                        translationViewModel.apply {

                            bookName.value =
                                bibleViewModel.selectedBook.value!!.bookName

                            chapterNum.value =
                                bibleViewModel.selectedChapter.value

                            verseNum.value = verse.verseNum
                        }


                        bottomSheet = TranslationBibleBottomSheetFragment.newInstance().apply {

                            showNow(this@ChapterFragment.parentFragmentManager, "translation")
                        }
                    })
        })
    }

}
package com.islam.hesn.myapplication.bible.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.home.changeToolbarTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*
import kotlinx.android.synthetic.main.layout_dialog_bible_fast_navigation.*

@AndroidEntryPoint
class BookFragment : Fragment() {


    private lateinit var selectedChapter: Chapter
    private val bibleViewModel: BibleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeToolbarTitle(bibleViewModel.selectedBook.value!!.bookName)
        observeData()
        getChapters()
        fabJump.setOnClickListener {
            fabJump.isExpanded = !fabJump.isExpanded
        }
        btFastForwardDone.setOnClickListener {

            fabJump.isExpanded = !fabJump.isExpanded
            bibleViewModel.selectedChapter.value = selectedChapter.chapterNum
            findNavController().navigate(R.id.chapterFragment)
        }
        btCancel.setOnClickListener { fabJump.isExpanded = !fabJump.isExpanded }
    }

    private fun getChapters() {
        bibleViewModel.getChaptersForSelectedBook()
    }

    private fun observeData() {
        bibleViewModel.chapterModels.observe(viewLifecycleOwner, {
            list.adapter =
                BibleMainRecyclerViewAdapter(
                    chapters = it,
                    state = AdapterStateBibleEnum.CHAPTERS,
                    onChapterItemClick = { chapterNum ->
                        bibleViewModel.selectedChapter.value = chapterNum
                        findNavController().navigate(R.id.chapterFragment)
                    })
            setupFastForwardSpinnerAdapter(it!!)
        })
    }

    private fun setupFastForwardSpinnerAdapter(chapters: List<Chapter>) {

        lateinit var verses: List<Verse>

        setupSpinnerArrayAdapter(chapters.map { it.chapterNum }, spinnerChapter)

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

    private fun setupSpinnerArrayAdapter(list: List<Any>, spinner: Spinner) {
        ArrayAdapter(
            requireContext(),
            R.layout.layout_spinner_drop_down_resource,
            list

        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.layout_spinner_drop_down_resource)
            spinner.adapter = adapter
        }
    }

}
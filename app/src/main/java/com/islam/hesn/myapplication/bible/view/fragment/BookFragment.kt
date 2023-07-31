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
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum
import com.islam.hesn.myapplication.bible.view.BibleMainRecyclerViewAdapter
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.databinding.FragmentBookBinding
import com.islam.hesn.myapplication.databinding.LayoutDialogBibleChapterFastNavigationBinding
import com.islam.hesn.myapplication.utils.changeToolbarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialogBinding: LayoutDialogBibleChapterFastNavigationBinding

    private lateinit var selectedChapter: Chapter
    private val bibleViewModel: BibleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentBookBinding.inflate(inflater, container, false)
        dialogBinding = binding.layoutDialogBibleChapterFastNavigation
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "BookFragment")
        changeToolbarTitle("سفر  ${bibleViewModel.selectedTitle.value!!}")
        setRefreshListener()
        setListDivider()
        observeData()
        getChapters()

        val fabJump = binding.fabJump
        fabJump.setOnClickListener {
            fabJump.isExpanded = !fabJump.isExpanded
        }

        dialogBinding.btFastForwardDone.setOnClickListener {

            fabJump.isExpanded = !fabJump.isExpanded
            bibleViewModel.selectedChapter.value = selectedChapter
            findNavController().navigate(R.id.chapterFragment)
        }
        dialogBinding.btCancel.setOnClickListener { fabJump.isExpanded = !fabJump.isExpanded }
    }

    private fun setListDivider() {
        binding.rvChaptersList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun getChapters() {
        bibleViewModel.getChaptersForSelectedBook(bibleViewModel.translationName.value!!,
            bibleViewModel.selectedBook.value!!.bookNum)
    }

    private fun observeData() {
        bibleViewModel.chapterModels.observe(viewLifecycleOwner) {
            binding.rvChaptersList.adapter =
                BibleMainRecyclerViewAdapter(
                    chapters = it,
                    state = AdapterStateBibleEnum.CHAPTERS,
                    isVerse = false,
                    onChapterItemClick = { chapterNum ->
                        bibleViewModel.selectedChapter.value = chapterNum
                        findNavController().navigate(R.id.chapterFragment)
                    })
            setupFastForwardSpinnerAdapter(it!!)
        }

        bibleViewModel.loading.observe(viewLifecycleOwner) { isVisible ->

            val progressBible = binding.progressBook
            if (isVisible) {
                progressBible.visibility = View.VISIBLE
                progressBible.playAnimation()
            } else {
                progressBible.visibility = View.GONE
                progressBible.cancelAnimation()
                binding.refreshBook.isRefreshing = false
            }

        }

        bibleViewModel.error.observe(viewLifecycleOwner) { isError ->

            val errorBook = binding.errorBook
            val errorTextBook = binding.errorTextBook
            if (isError) {
                errorBook.visibility = View.VISIBLE
                errorTextBook.visibility = View.VISIBLE
            } else {
                errorBook.visibility = View.GONE
                errorTextBook.visibility = View.GONE
            }
        }

        bibleViewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            binding.apply {
                if (isSuccess) {
                    binding.fabJump.show()
                    binding.refreshBook.visibility = View.VISIBLE
                } else {
                    binding.fabJump.hide()
                    binding.refreshBook.visibility = View.GONE
                }
            }
        }
    }

    private fun setupFastForwardSpinnerAdapter(chapters: List<Chapter>) {

        lateinit var verses: List<Verse>

        setupSpinnerArrayAdapter(chapters.map { it.chapterNum }, dialogBinding.spinnerChapter)

        dialogBinding.spinnerChapter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                selectedChapter = chapters[position]
                verses = selectedChapter.verses
                setupSpinnerArrayAdapter(verses.map { it.verseNum }, dialogBinding.spinnerVerse)

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

    private fun setRefreshListener() {
        binding.refreshBook.setOnRefreshListener {
            getChapters()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
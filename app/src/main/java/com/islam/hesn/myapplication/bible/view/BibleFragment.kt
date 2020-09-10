package com.islam.hesn.myapplication.bible.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.AdapterStateBibleEnum.BOOKS
import com.islam.hesn.myapplication.bible.BibleLangEnum
import com.islam.hesn.myapplication.bible.viewmodel.BibleViewModel
import com.islam.hesn.myapplication.home.changeToolbarTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*

@AndroidEntryPoint
class BibleFragment : Fragment() {

    private val bibleViewModel: BibleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_quran_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        changeToolbarTitle(getString(R.string.bible))
        observeData()
        getBooks()
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
//                    bibleViewModel.selectedBook.value = bookNum
//                    findNavController().navigate(R.id.surahFragment)
                    })
        })
    }

}
package com.islam.hesn.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.islam.hesn.myapplication.AdapterStateEnum.QURAN_SURAH_LIST
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.adapter.MySurahRecyclerViewAdapter
import com.islam.hesn.myapplication.viewmodel.QuranViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*

@AndroidEntryPoint
class QuranFragment : Fragment() {

    private val quranViewModel: QuranViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_quran_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        changeToolbarTitle()
        observeData()
        getSurahs()
    }

    private fun getSurahs() {
        quranViewModel.getAllArabicSurah()
    }

    private fun observeData() {
        quranViewModel.surahs.observe(viewLifecycleOwner, {
            list.adapter =
                MySurahRecyclerViewAdapter(it, QURAN_SURAH_LIST, { surahId ->
                    quranViewModel.surahId.value = surahId
                    findNavController().navigate(R.id.surahFragment)
                })
        })
    }

    private fun changeToolbarTitle() {
        val toolbar = activity?.findViewById<MaterialToolbar>(R.id.toolbar)
        val title = toolbar?.findViewById<TextView>(R.id.toolbarText)

        title?.text = getString(R.string.quran)
    }

}
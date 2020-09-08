package com.islam.hesn.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.islam.hesn.myapplication.AdapterStateEnum.QURAN_SURAH
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.adapter.MySurahRecyclerViewAdapter
import com.islam.hesn.myapplication.changeToolbarTitle
import com.islam.hesn.myapplication.createDialog
import com.islam.hesn.myapplication.model.response.arabic.SurahItem
import com.islam.hesn.myapplication.viewmodel.AyaTranslationViewModel
import com.islam.hesn.myapplication.viewmodel.QuranViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_surah.*

@AndroidEntryPoint
class SurahFragment : Fragment() {

    private val quranViewModel: QuranViewModel by activityViewModels()
    private val ayaViewModel: AyaTranslationViewModel by activityViewModels()

    private lateinit var bottomSheet: TranslationBottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_surah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViewModelObservers()
        fab.setOnClickListener { surahRecyclerView.smoothScrollToPosition(0) }
    }

    private fun setupViewModelObservers() {
        quranViewModel.surahId.observe(viewLifecycleOwner, Observer { surahId ->

            val surah = quranViewModel.ayat.value?.filter { it.sura_id == surahId }
            changeToolbarTitle(surah!!.first().sura_name)

            surahRecyclerView.adapter = MySurahRecyclerViewAdapter(
                surah as ArrayList<SurahItem>,
                QURAN_SURAH, onAyaItemClick = { surahId, ayaId ->

                    ayaViewModel.ayaNum.value = ayaId
                    ayaViewModel.suraNum.value = surahId
                    bottomSheet = TranslationBottomSheetFragment.newInstance().apply {

                        showNow(this@SurahFragment.parentFragmentManager, "translation")
                    }
                }
            )

        })

        ayaViewModel.aya.observe(viewLifecycleOwner, { aya ->

            this@SurahFragment.createDialog(aya.aya, aya.translation)
        })

        ayaViewModel.loading.observe(viewLifecycleOwner, Observer { isVisible ->
            progress.visibility = if (isVisible) View.VISIBLE else View.GONE
        })
    }

}
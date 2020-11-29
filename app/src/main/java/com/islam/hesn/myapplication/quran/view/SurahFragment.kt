package com.islam.hesn.myapplication.quran.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.home.changeToolbarTitle
import com.islam.hesn.myapplication.home.createDialog
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.islam.hesn.myapplication.quran.viewmodel.AyaTranslationViewModel
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_surah.*


@AndroidEntryPoint
class SurahFragment : Fragment() {

    private lateinit var surahName: String
    private val quranViewModel: QuranViewModel by activityViewModels()
    private val ayaViewModel: AyaTranslationViewModel by activityViewModels()

    private lateinit var bottomSheet: TranslationQuranBottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_surah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListDivider()
        setupViewModelObservers()
        fab.setOnClickListener { surahRecyclerView.smoothScrollToPosition(0) }
    }

    private fun setListDivider() {
        surahRecyclerView.addItemDecoration(DividerItemDecoration(context,
            DividerItemDecoration.VERTICAL))
    }

    private fun setupViewModelObservers() {
        quranViewModel.surahId.observe(viewLifecycleOwner, { surahId ->

            val surah =
                quranViewModel.ayat.value?.filter {
                    it.sura_id == if (quranViewModel.isBookMark.value!!.not()) surahId
                    else Prefs.getInt(
                        BOOKMARK_SURAH_NUMBER,
                        1)
                }
            surahName = surah!!.first().sura_name
            changeToolbarTitle("سورة $surahName")

            surahRecyclerView.adapter = MySurahRecyclerViewAdapter(
                surah as ArrayList<AyaItem>,
                QURAN_SURAH, onAyaItemClick = { surahId, ayaId ->

                    ayaViewModel.ayaNum.value = ayaId
                    ayaViewModel.suraNum.value = surahId
                    bottomSheet = TranslationQuranBottomSheetFragment.newInstance().apply {

                        showNow(this@SurahFragment.parentFragmentManager, "translation")
                    }
                },
                onLastReadClick = { surahId, ayaId ->
                    quranViewModel.surahId.removeObservers(viewLifecycleOwner)

                    Prefs.putAny(BOOKMARK_SURAH_NUMBER, surahId)
                    Prefs.putAny(BOOKMARK_AYA_NUMBER, ayaId)

                    Prefs.putAny(BOOKMARK_SURAH_NAME, surahName)

                    quranViewModel.surahId.value = quranViewModel.surahId.value

                    requireContext().showSnackBar(surahRecyclerView,
                        getString(R.string.bookmark_saved_successfully))

                }
            )

            quranViewModel.apply {
                val ayaScrollId =
                    if (isBookMark.value!!) Prefs.getInt(BOOKMARK_AYA_NUMBER, 1) - 1
                    else ayaFastForwardId.value!! - 1
                surahRecyclerView.scrollToPosition(ayaScrollId)
            }
        })

        ayaViewModel.aya.observe(viewLifecycleOwner, { aya ->
            aya?.let {
                this@SurahFragment.createDialog(aya.aya, aya.translation)
                ayaViewModel.aya.value = null
            }

        })

        ayaViewModel.loading.observe(viewLifecycleOwner, { isVisible ->
            progress.visibility = if (isVisible) View.VISIBLE else View.GONE
        })
    }


}
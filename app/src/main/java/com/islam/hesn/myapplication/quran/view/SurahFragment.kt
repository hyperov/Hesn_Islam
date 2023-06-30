package com.islam.hesn.myapplication.quran.view

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
import com.islam.hesn.myapplication.databinding.FragmentSurahBinding
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.islam.hesn.myapplication.quran.viewmodel.AyaTranslationViewModel
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SurahFragment : Fragment() {

    private var _binding: FragmentSurahBinding? = null
    private val binding get() = _binding!!

    private lateinit var surahName: String
    private val quranViewModel: QuranViewModel by activityViewModels()
    private val ayaViewModel: AyaTranslationViewModel by activityViewModels()

    private lateinit var bottomSheet: TranslationQuranBottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSurahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "SurahFragment");
        setListDivider()
        setupViewModelObservers()
        binding.fab.setOnClickListener { binding.surahRecyclerView.smoothScrollToPosition(0) }
    }

    private fun setListDivider() {
        binding.surahRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupViewModelObservers() {
        quranViewModel.surahId.observe(viewLifecycleOwner) { surahId ->

            val surah =
                quranViewModel.ayat.value?.filter {
                    it.sura_id == if (quranViewModel.isBookMark.value!!.not()) surahId
                    else Prefs.getInt(
                        BOOKMARK_SURAH_NUMBER,
                        1
                    )
                }
            surahName = surah!!.first().sura_name
            changeToolbarTitle("سورة $surahName")

            binding.surahRecyclerView.adapter = MySurahRecyclerViewAdapter(
                surah as ArrayList<AyaItem>,
                QURAN_SURAH, onAyaItemClick = { surahId, ayaId ->

                    if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                        val bottomNavView: BottomNavigationView =
                            activity?.findViewById(R.id.bottomNavigation)!!

                        requireContext().showSnackBar(
                            binding.surahRecyclerView,
                            bottomNavView,
                            getString(R.string.error_no_connection),
                            android.R.color.holo_red_light
                        )

                        return@MySurahRecyclerViewAdapter
                    }

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
                    val bottomNavView: BottomNavigationView =
                        activity?.findViewById(R.id.bottomNavigation)!!
                    requireContext().showSnackBar(
                        binding.surahRecyclerView, bottomNavView,
                        getString(R.string.bookmark_saved_successfully)
                    )

                }
            )

            quranViewModel.apply {
                val ayaScrollId =
                    if (isBookMark.value!!) Prefs.getInt(BOOKMARK_AYA_NUMBER, 1) - 1
                    else ayaFastForwardId.value!! - 1
                binding.surahRecyclerView.scrollToPosition(ayaScrollId)
            }
        }

        ayaViewModel.aya.observe(viewLifecycleOwner) { aya ->
            aya?.let {
                this@SurahFragment.createDialog(aya.aya, aya.translation)
                ayaViewModel.aya.value = null
                Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
            }

        }

        ayaViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.progress.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        ayaViewModel.error.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                val bottomNavView: BottomNavigationView =
                    activity?.findViewById(R.id.bottomNavigation)!!
                requireContext().showSnackBar(
                    binding.surahRecyclerView,
                    bottomNavView,
                    getString(R.string.error_bible_quran_translation_api),
                    android.R.color.holo_red_light
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
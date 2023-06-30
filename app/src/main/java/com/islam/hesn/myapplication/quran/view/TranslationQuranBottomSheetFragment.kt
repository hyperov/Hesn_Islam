package com.islam.hesn.myapplication.quran.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.LayoutQuranTranslationOptionsBottomSheetBinding
import com.islam.hesn.myapplication.quran.model.response.translation.TranslationsQuranOptionsEnum.*
import com.islam.hesn.myapplication.quran.viewmodel.AyaTranslationViewModel
import kotlinx.coroutines.launch

class TranslationQuranBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: LayoutQuranTranslationOptionsBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val ayaViewModel: AyaTranslationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding =
            LayoutQuranTranslationOptionsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance()
            .setCustomKey("SCREEN", "TranslationQuranBottomSheetFragment")
        setupViews()
    }

    private fun setupViews() {
        binding.bottomsheet.children.forEach { it.setOnClickListener(this) }
    }

    companion object {
        @JvmStatic
        fun newInstance(): TranslationQuranBottomSheetFragment {
            return TranslationQuranBottomSheetFragment()
        }
    }

    override fun onClick(v: View?) {

        var lang = ""
        ayaViewModel.apply {
            binding.apply {
                when (v) {
                    english -> {
                        lang = ENGLISH.lang
                    }
                    french -> {
                        lang = FRENCH.lang
                    }
                    german -> {
                        lang = GERMAN.lang
                    }
                    spanish -> {
                        lang = SPANISH.lang
                    }
                    chinese -> {
                        lang = CHINESE.lang
                    }
                }
            }
            dismissAllowingStateLoss()

            FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "QURAN_TRANSLATION")
            lifecycleScope.launch {
                ayaViewModel.getAyah(lang, suraNum.value!!, ayaNum.value!!)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
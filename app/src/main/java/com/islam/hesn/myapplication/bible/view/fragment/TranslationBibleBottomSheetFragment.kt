package com.islam.hesn.myapplication.bible.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.model.response.translation.TranslationsOptionsEnum.*
import com.islam.hesn.myapplication.quran.viewmodel.AyaTranslationViewModel
import kotlinx.android.synthetic.main.layout_translation_options_bottom_sheet.*
import kotlinx.coroutines.launch

class TranslationBibleBottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private val ayaViewModel: AyaTranslationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_Demo_BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_translation_options_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        bottomsheet.children.forEach { it.setOnClickListener(this) }
    }

    companion object {
        @JvmStatic
        fun newInstance(): TranslationBibleBottomSheetFragment {
            return TranslationBibleBottomSheetFragment()
        }
    }

    override fun onClick(v: View?) {

        var lang = ""
        ayaViewModel.apply {
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
            dismissAllowingStateLoss()

            lifecycleScope.launch {
                ayaViewModel.getAyah(lang, suraNum.value!!, ayaNum.value!!)
            }

        }
    }
}
package com.islam.hesn.myapplication.quran.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH_LIST
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import com.islam.hesn.myapplication.utils.MinMaxFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quran_list.*
import kotlinx.android.synthetic.main.layout_dialog_surah_fast_navigation.*


@AndroidEntryPoint
class QuranFragment : Fragment(), TextView.OnEditorActionListener {

    private val quranViewModel: QuranViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_quran_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetFastForward()
        observeData()
        getSurahs()
        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
        setFastForwardListener()
    }

    private fun setFastForwardListener() {
        fabJump.setOnClickListener {
            fabJump.isExpanded = !fabJump.isExpanded
        }
        btFastForwardDone.setOnClickListener {

            fabJump.isExpanded = !fabJump.isExpanded
            etAya.text.toString().apply {
                if (isNotBlank()) {
                    quranViewModel.ayaFastForwardId.value = this.toInt()
                    findNavController().navigate(R.id.surahFragment)
                }
            }
        }
    }

    private fun resetFastForward() {
        quranViewModel.ayaFastForwardId.value = 0
    }

    private fun setSearchTypingListener() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.toString().isNullOrBlank()) {
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_search,
                        0
                    )
                } else if (s?.toString()?.isNotBlank()!! && s.toString().isNotEmpty()) {
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(
                        android.R.drawable.ic_menu_close_clear_cancel,
                        0,
                        R.drawable.ic_search,
                        0
                    )
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {
        etSearch.setOnTouchListener(OnTouchListener { v, event ->

            val DRAWABLE_LEFT = 0
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                etSearch.compoundDrawables[DRAWABLE_RIGHT]?.let {
                    if (event.rawX >= etSearch.right - it.bounds.width()) {

                        gotoSearchScreen(etSearch.text.toString())
                        return@OnTouchListener true
                    }
                }
                etSearch.compoundDrawables[DRAWABLE_LEFT]?.let {
                    if (event.rawX <= it.bounds.width() + 2 * etSearch.paddingLeft) {
                        etSearch.editableText.clear()
                        return@OnTouchListener true
                    }
                }
            }
            false
        })
    }

    private fun getSurahs() {
        quranViewModel.getAllArabicSurah()
    }

    private fun observeData() {
        quranViewModel.surahs.observe(viewLifecycleOwner, {
            list.adapter =
                MySurahRecyclerViewAdapter(it, QURAN_SURAH_LIST, { surahId ->
                    if (quranViewModel.surahId.value != surahId)
                        quranViewModel.surahId.value = surahId
                    findNavController().navigate(R.id.surahFragment)
                })
            setupFastForwardSpinnerAdapter()
        })
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            gotoSearchScreen(etSearch.text.toString())
        }
        return true

    }

    private fun gotoSearchScreen(searchText: String) {
        searchViewModel.apply {

            searchQuery.value = searchText
            isFromQuranScreen.value = true
            ayat.postValue(quranViewModel.ayat.value)
        }
        findNavController().navigate(R.id.searchFragment)
    }

    private fun setupFastForwardSpinnerAdapter() {

        ArrayAdapter(
            requireContext(),
            R.layout.layout_spinner_drop_down_resource,
            quranViewModel.surahs.value?.map { it -> it.sura_name }!!

        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.layout_spinner_drop_down_resource)
            spinnerSurah.adapter = adapter
        }

        spinnerSurah.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                quranViewModel.surahId.value = position + 1
                setupAyaMinMax(position)
            }

            private fun setupAyaMinMax(position: Int) {
                val ayaCount = quranViewModel.ayat.value?.count { it.sura_id == position + 1 }
                etAya.filters = arrayOf<InputFilter>(MinMaxFilter(1, ayaCount!!))
                etAya.hint = "1 الى $ayaCount"
                tvEnterAyaNumberFromTo.text = "ادخل رقم الأية من 1 الى $ayaCount"

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

}
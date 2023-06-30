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
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.FragmentQuranListBinding
import com.islam.hesn.myapplication.databinding.LayoutDialogSurahFastNavigationBinding
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH_LIST
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.search.viewmodel.SearchViewModel
import com.islam.hesn.myapplication.utils.COUNTER_FOR_REVIEW
import com.islam.hesn.myapplication.utils.MinMaxFilter
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.putAny
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuranFragment : Fragment(), TextView.OnEditorActionListener {

    private var _binding: FragmentQuranListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialogBinding: LayoutDialogSurahFastNavigationBinding


    private val quranViewModel: QuranViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentQuranListBinding.inflate(inflater, container, false)
        dialogBinding = binding.layoutDialogSurahFastNavigation2

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "QuranFragment")
        setListDivider()
        resetFastForward()
        observeData()
        getSurahs()
        binding.etSearchQuran.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
        setFastForwardListener()
    }

    private fun setListDivider() {
        binding.list.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setFastForwardListener() {

        val fabJump = binding.fabJump
        fabJump.setOnClickListener {
            fabJump.isExpanded = !fabJump.isExpanded
        }

        dialogBinding.apply {
            btFastForwardDone.setOnClickListener {

                fabJump.isExpanded = !fabJump.isExpanded
                etAya.text.toString().apply {
                    if (isNotBlank()) {
                        quranViewModel.ayaFastForwardId.value = this.toInt()
                        quranViewModel.isBookMark.value = false
                        quranViewModel.surahId.value = spinnerSurah.selectedItemPosition + 1
                        findNavController().navigate(R.id.surahFragment)
                        Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0) + 1)
                    }
                }
            }
            btCancel.setOnClickListener { fabJump.isExpanded = !fabJump.isExpanded }
        }
    }

    private fun resetFastForward() {
        quranViewModel.ayaFastForwardId.value = 0
        setupAyaMinMax(dialogBinding.spinnerSurah.selectedItemPosition)
    }

    private fun setSearchTypingListener() {
        binding.etSearchQuran.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.etSearchQuran.let {
                    if (s?.toString().isNullOrBlank()) {
                        binding.etSearchQuran.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_search,
                            0
                        )

                    } else if (s?.toString()?.isNotBlank()!! && s.toString().isNotEmpty()) {
                        binding.etSearchQuran.setCompoundDrawablesWithIntrinsicBounds(
                            android.R.drawable.ic_menu_close_clear_cancel,
                            0,
                            R.drawable.ic_search,
                            0
                        )

                    }
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {
        binding.apply {
            etSearchQuran.setOnTouchListener(OnTouchListener { _, event ->

                val DRAWABLE_LEFT = 0
                val DRAWABLE_RIGHT = 2

                if (event.action == MotionEvent.ACTION_UP) {
                    etSearchQuran.compoundDrawables[DRAWABLE_RIGHT]?.let {
                        if (event.rawX >= etSearchQuran.right - it.bounds.width()) {
                            if (etSearchQuran.text!!.isNotEmpty())
                                gotoSearchScreen(etSearchQuran.text.toString())
                            return@OnTouchListener true
                        }
                    }
                    etSearchQuran.compoundDrawables[DRAWABLE_LEFT]?.let {
                        if (event.rawX <= it.bounds.width() + 2 * etSearchQuran.paddingLeft) {
                            etSearchQuran.editableText.clear()
                            return@OnTouchListener true
                        }
                    }
                }
                false
            })
        }
    }

    private fun getSurahs() {
        if (quranViewModel.ayat.value.isNullOrEmpty())
            quranViewModel.getAllArabicSurah()
        FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "QURAN")
    }

    private fun observeData() {
        quranViewModel.surahs.observe(viewLifecycleOwner) {
            binding.list.adapter =
                MySurahRecyclerViewAdapter(it, QURAN_SURAH_LIST, { surahId ->

                    if (quranViewModel.surahId.value != surahId)
                        quranViewModel.surahId.value = surahId
                    quranViewModel.isBookMark.value = false
                    findNavController().navigate(R.id.surahFragment)
                })
            setupFastForwardSpinnerAdapter()
        }

        quranViewModel.loading.observe(viewLifecycleOwner) { isVisible ->
            binding.list.visibility = if (isVisible) View.GONE else View.VISIBLE
            val fabJump = binding.fabJump
            if (isVisible) {
                fabJump.hide()
            } else {
                fabJump.show()
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        binding.apply {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                etSearchQuran.let {
                    if (etSearchQuran.text!!.isNotEmpty())
                        gotoSearchScreen(etSearchQuran.text.toString())
                }
            }
            return true

        }
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
            dialogBinding.spinnerSurah.adapter = adapter
        }

        dialogBinding.spinnerSurah.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {

                    setupAyaMinMax(position)
                }


                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun setupAyaMinMax(position: Int) {
        val ayaCount = quranViewModel.ayat.value?.count { it.sura_id == position + 1 }
        dialogBinding.apply {
            ayaCount?.let {
                etAya.filters = arrayOf<InputFilter>(MinMaxFilter(1, ayaCount))
                etAya.hint = "1 الى $ayaCount"
                tvEnterAyaNumberFromTo.text = "ادخل رقم الأية من 1 الى $ayaCount"
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
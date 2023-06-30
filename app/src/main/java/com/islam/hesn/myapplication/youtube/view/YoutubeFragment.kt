package com.islam.hesn.myapplication.youtube.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.FragmentYoutubeBinding
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel

class YoutubeFragment : Fragment(), TextView.OnEditorActionListener {

    //R.layout.fragment_youtube
    private var _binding: FragmentYoutubeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val youtubeSearchViewModel: YoutubeSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentYoutubeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tabLayout
        tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, YoutubeFirstChannelFragment()).commit()
                    1 -> childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, YoutubeSecondChannelFragment()).commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        setDefaultScreen()
        binding.etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
    }

    private fun setDefaultScreen() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, YoutubeFirstChannelFragment()).commit()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        val etSearch = binding.etSearch
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (etSearch.text!!.isNotEmpty())
                gotoSearchScreen(etSearch.text.toString())
        }
        return true
    }

    private fun setSearchTypingListener() {
        val etSearch = binding.etSearch
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                etSearch.let {
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
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchIconClick() {
        val etSearch = binding.etSearch
        etSearch.setOnTouchListener(View.OnTouchListener { _, event ->

            val DRAWABLE_LEFT = 0
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                etSearch.compoundDrawables[DRAWABLE_RIGHT]?.let {
                    if (event.rawX >= etSearch.right - it.bounds.width()) {
                        if (etSearch.text!!.isNotEmpty())
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

    private fun gotoSearchScreen(searchText: String) {
        if (binding.etSearch.text!!.isNotEmpty()) {
            youtubeSearchViewModel.apply {
                searchQuery.value = searchText
                selectedTabPosition.value = binding.tabLayout.selectedTabPosition
            }
            findNavController().navigate(R.id.youtubeSearchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
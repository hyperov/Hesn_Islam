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
import com.google.android.material.tabs.TabLayoutMediator
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import kotlinx.android.synthetic.main.fragment_youtube.*

class YoutubeFragment : Fragment(), TextView.OnEditorActionListener {

    private val youtubeSearchViewModel: YoutubeSearchViewModel by activityViewModels()

    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pagerAdapter = ViewPagerAdapter(this)
        pager.adapter = pagerAdapter
        tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.main_channel)
                1 -> tab.text = getString(R.string.education_channel)
            }

        }.attach()

        etSearch.setOnEditorActionListener(this)
        setSearchIconClick()
        setSearchTypingListener()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (etSearch.text!!.isNotEmpty())
                gotoSearchScreen(etSearch.text.toString())
        }
        return true
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
        etSearch.setOnTouchListener(View.OnTouchListener { v, event ->

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
        if (etSearch.text!!.isNotEmpty()) {
            youtubeSearchViewModel.apply {
                searchQuery.value = searchText
                selectedTabPosition.value = tabLayout.selectedTabPosition
            }
            findNavController().navigate(R.id.youtubeSearchFragment)
        }
    }

}
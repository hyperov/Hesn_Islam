package com.islam.hesn.myapplication.more

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.utils.*
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_more.*

@AndroidEntryPoint
class MoreFragment : Fragment(), View.OnClickListener {

    private val quranViewModel: QuranViewModel by activityViewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        if (Prefs.contains(BOOKMARK_SURAH_NAME)) {

            tvLastRead.text =
                getString(R.string.last_read) + " ( " + Prefs.getString(BOOKMARK_SURAH_NAME,
                    "الفاتحة") + " الأية " + Prefs.getInt(
                    BOOKMARK_AYA_NUMBER, 1) + ")"
        }
    }

    private fun setClickListeners() {
        tvAboutUs.setOnClickListener(this)
        tvContactUs.setOnClickListener(this)
        tvLastRead.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvAboutUs -> {
                youtubePlayerViewModel.videoId.value = getString(R.string.about_us_video_id)
                youtubePlayerViewModel.videoTitle.value = getString(R.string.about_us_video_title)
                findNavController().navigate(R.id.youtubePlayerFragment)
            }
            tvContactUs -> {
            }
            tvLastRead -> {

                if (Prefs.contains(BOOKMARK_SURAH_NUMBER) && Prefs.contains(BOOKMARK_AYA_NUMBER)) {
                    quranViewModel.isBookMark.value = true
                    if (quranViewModel.ayat.value.isNullOrEmpty())
                        quranViewModel.getAllArabicSurah()
                    //value isn't important..but only to activate observer in surah fragment
                    quranViewModel.surahId.value = quranViewModel.surahId.value
                    findNavController().navigate(R.id.surahFragment)
                } else {
                    requireContext().showSnackBar(moreLayout, getString(R.string.no_bookmarks))

                }
            }
        }
    }


}
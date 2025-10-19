package com.islam.hesn.myapplication.more

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.contactus.ContactUsBottomSheetFragment
import com.islam.hesn.myapplication.databinding.FragmentMoreBinding
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.utils.*
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private val quranViewModel: QuranViewModel by activityViewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MoreFragment")
        setClickListeners()
        if (Prefs.contains(BOOKMARK_SURAH_NAME)) {

            binding.tvLastRead.text =
                getString(R.string.last_read) + "\n" + " ( " + Prefs.getString(
                    BOOKMARK_SURAH_NAME,
                    "الفاتحة"
                ) + " الأية " + Prefs.getInt(
                    BOOKMARK_AYA_NUMBER, 1
                ) + ")"
        }
    }

    private fun setClickListeners() {
        binding.apply {
            cvAboutUs.setOnClickListener(this@MoreFragment)
            cvContactUs.setOnClickListener(this@MoreFragment)
            cvLastRead.setOnClickListener(this@MoreFragment)
        }
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                cvAboutUs -> {
                    youtubePlayerViewModel.videoId.value = getString(R.string.about_us_video_id)
                    youtubePlayerViewModel.videoTitle.value =
                        getString(R.string.about_us_video_title)
                    if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

                        val bottomNavView: BottomNavigationView =
                            activity?.findViewById(R.id.bottomNavigation)!!

                        requireContext().showSnackBar(
                            requireActivity().findViewById(android.R.id.content),
                            bottomNavView,
                            getString(R.string.error_no_connection),
                            android.R.color.holo_red_light
                        )
                    } else
                        findNavController().navigate(R.id.youtubePlayerFragment)
                }
                cvContactUs -> {
                     ContactUsBottomSheetFragment.newInstance().apply {
                        showNow(this@MoreFragment.parentFragmentManager, "translation")
                    }
                }
                cvLastRead -> {

                    if (Prefs.contains(BOOKMARK_SURAH_NUMBER) && Prefs.contains(BOOKMARK_AYA_NUMBER)) {
                        quranViewModel.isBookMark.value = true
                        if (quranViewModel.ayat.value.isNullOrEmpty())
                            quranViewModel.getAllArabicSurah()
                        //value isn't important..but only to activate observer in surah fragment
                        quranViewModel.surahId.value = quranViewModel.surahId.value
                        findNavController().navigate(R.id.surahFragment)
                    } else {
                        val bottomNavView: BottomNavigationView =
                            activity?.findViewById(R.id.bottomNavigation)!!
                        requireContext().showSnackBar(
                            moreLayout,
                            bottomNavView,
                            getString(R.string.no_bookmarks)
                        )

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
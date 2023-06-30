package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.FragmentYoutubeFirstChannelBinding
import com.islam.hesn.myapplication.utils.IS_CONNECTED
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.openYoutubeChannelIntent
import com.islam.hesn.myapplication.utils.showSnackBar
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YoutubeFirstChannelFragment : Fragment() {

    private var _binding: FragmentYoutubeFirstChannelBinding? = null
    private val binding get() = _binding!!

    private val youtubeViewModel: YoutubeViewModel by viewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    private val pagingAdapter =
        YoutubeRecyclerViewPagingAdapter(VideoComparator) { videoId, videoTitle ->
            youtubePlayerViewModel.videoId.value = videoId
            youtubePlayerViewModel.videoTitle.value = videoTitle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentYoutubeFirstChannelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "YoutubeFirstChannelFragment")
        binding.videoList.adapter = pagingAdapter
        setRefreshListener()
        getVideos()
    }


    private fun getVideos() {
        if (Prefs.getBoolean(IS_CONNECTED, true).not()) {

            val bottomNavView: BottomNavigationView =
                activity?.findViewById(R.id.bottomNavigation)!!

            requireContext().showSnackBar(
                requireActivity().findViewById(android.R.id.content),
                bottomNavView,
                getString(R.string.error_no_connection),
                android.R.color.holo_red_light
            )
        }
        FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "MAIN_YOUTUBE_CHANNEL_HESN_ISLAM")
        youtubeViewModel.getYoutubeChannelVideos(getString(R.string.main_channel_playlist_id))
        getPagingMovies()
    }

    private fun setRefreshListener() {
        binding.refresh.setOnRefreshListener {
            pagingAdapter.refresh()

        }
    }

    private fun getPagingMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.apply {
                pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                    refresh.isRefreshing = false
                    progressYoutube.isVisible = loadStates.refresh is LoadState.Loading
                    videoList.isVisible = loadStates.refresh is LoadState.NotLoading
                    videoList.isVisible = loadStates.refresh !is LoadState.Error
                    error.isVisible = loadStates.refresh is LoadState.Error
                    errorText.isVisible = loadStates.refresh is LoadState.Error
                    if (loadStates.refresh is LoadState.Error) {
                        val error = (loadStates.refresh as LoadState.Error).error
                        if (error.message!!.contains("quotaExceeded"))
                            openYoutubeChannelIntent(getString(R.string.main_channel_playlist_id))
                    }


                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            youtubeViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.refresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
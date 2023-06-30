package com.islam.hesn.myapplication.youtube.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.YoutubeSearchFragmentBinding
import com.islam.hesn.myapplication.utils.IS_CONNECTED
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.changeToolbarTitle
import com.islam.hesn.myapplication.utils.showSnackBar
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class YoutubeSearchFragment : Fragment() {

    //R.layout.fragment_youtube_search
    private var _binding: YoutubeSearchFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var channelId: String = ""
    private val youtubeSearchViewModel: YoutubeSearchViewModel by activityViewModels()
    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    private val pagingAdapter =
        YoutubeRecyclerViewSearchPagingAdapter(VideoSearchComparator) { videoId, videoTitle ->
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
    ): View {
        _binding = YoutubeSearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "YoutubeSearchFragment")
        binding.tvSearchKeyWord.text = youtubeSearchViewModel.searchQuery.value
        changeToolbarTitle(
            when (youtubeSearchViewModel.selectedTabPosition.value) {
                0 -> getString(R.string.main_channel)
                1 -> getString(R.string.education_channel)
                else -> ""
            }
        )
        binding.videosList.adapter = pagingAdapter
        channelId = when (youtubeSearchViewModel.selectedTabPosition.value) {
            0 -> getString(R.string.main_channel_id)
            1 -> getString(R.string.education_channel_id)
            else -> ""
        }

        when (youtubeSearchViewModel.selectedTabPosition.value) {
            0 -> FirebaseCrashlytics.getInstance().setCustomKey("REQUEST", "HESN_ISLAM_SEARCH")
            1 -> FirebaseCrashlytics.getInstance()
                .setCustomKey("REQUEST", "HESN_ISLAM_TAWAYA_SEARCH")
        }

        youtubeSearchViewModel.getSearchedYoutubeVideos(channelId)
        getPagingMovies()
    }


    private fun getPagingMovies() {
        viewLifecycleOwner.lifecycleScope.launch {

            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.apply {
                    progressYoutube.isVisible = loadStates.refresh is LoadState.Loading
                    videosList.isVisible = loadStates.refresh is LoadState.NotLoading
//                retry.isVisible = loadStates.refresh !is LoadState.Loading
                    videosList.isVisible = loadStates.refresh !is LoadState.Error
//                errorMsg.isVisible = loadStates.refresh is LoadState.Error
                    if (pagingAdapter.itemCount <= 0 && !loadStates.source.refresh.endOfPaginationReached && videosList.isVisible && progressYoutube.isVisible.not()) {
                        tvSearch.text = getString(R.string.no_results)
                        noResultsYoutube.isVisible = true
                    } else {
                        tvSearch.text = getString(R.string.search_results_for)
                        noResultsYoutube.isGone = true
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {

            youtubeSearchViewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.islam.hesn.myapplication.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.compose.content
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MoreWrapperFragment : Fragment() {

    private val youtubePlayerViewModel: YoutubePlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {

        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MoreWrapperFragment")
        MoreScreen(
            navigateToSurah = {
                findNavController().navigate(R.id.surahFragment)
            }, modifier = Modifier,
            navigateToYoutubePlayer = {
                youtubePlayerViewModel.setVideo(
                    id = getString(R.string.about_us_video_id),
                    title = getString(R.string.about_us_video_title)
                )
                // Navigate to YouTube tab - the host fragment will handle showing the player
                findNavController().navigate(R.id.youtubeFragment)
            }
        )
    }

}
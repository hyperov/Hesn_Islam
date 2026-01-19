package com.islam.hesn.myapplication.youtube.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import ui.theme.ColorAccent
import ui.theme.JanaFamily

@Composable
fun YoutubePlayerScreen(
    youtubePlayerViewModel: YoutubePlayerViewModel,
    onBack: () -> Unit,
) {
    val uiState by youtubePlayerViewModel.uiState.collectAsStateWithLifecycle()
    val videoId = uiState.videoId

    BackHandler {
        youtubePlayerViewModel.clearVideo()
        onBack()
    }

    if (videoId == null) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.no_results),
                fontFamily = JanaFamily,
                color = ColorAccent,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        YoutubePlayerViewComposable(
            videoId = videoId,
            onBack = onBack,
            modifier = Modifier.fillMaxSize()
        )
    }
}
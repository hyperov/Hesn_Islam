package com.islam.hesn.myapplication.youtube.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

    BackHandler(onBack = onBack)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (videoId == null) {
            Text(
                text = stringResource(id = R.string.no_results),
                fontFamily = JanaFamily,
                color = ColorAccent,
            )
        } else {
            YoutubePlayerViewComposable(modifier = Modifier.background(color = ColorAccent),
                videoId = videoId,
                title = uiState.videoTitle.orEmpty(),
            )
        }
    }
}
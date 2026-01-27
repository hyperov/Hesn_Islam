package com.islam.hesn.myapplication.youtube.view.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.view.components.YoutubeLoadingAnimation
import com.islam.hesn.myapplication.youtube.view.components.YoutubeNoResultsAnimation
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import ui.theme.ColorAccent
import ui.theme.ColorPrimaryDark
import ui.theme.JanaFamily

@Composable
fun YoutubeSearchScreen(
    youtubeSearchViewModel: YoutubeSearchViewModel,
    youtubePlayerViewModel: YoutubePlayerViewModel,
    onOpenPlayer: (String, String) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by youtubeSearchViewModel.uiState.collectAsStateWithLifecycle()
    // Collect the flow from StateFlow and then collect as paging items
    val currentFlow by youtubeSearchViewModel.flowState.collectAsStateWithLifecycle()
    val pagingItems = currentFlow.collectAsLazyPagingItems()

    val mainChannelId = stringResource(R.string.main_channel_id)
    val educationChannelId = stringResource(R.string.education_channel_id)

    LaunchedEffect(uiState.searchQuery, uiState.selectedTabPosition) {
        val channelId = if (uiState.selectedTabPosition == 1) {
            educationChannelId
        } else {
            mainChannelId
        }
        youtubeSearchViewModel.getSearchedYoutubeVideos(channelId)
    }

    BackHandler(onBack = onBack)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorPrimaryDark,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.search_results_for),
                    fontFamily = JanaFamily,
                    color = ColorAccent,
                )
                Text(
                    text = " ${uiState.searchQuery}",
                    fontFamily = JanaFamily,
                    color = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }

            if (pagingItems.loadState.refresh is androidx.paging.LoadState.Loading) {
                YoutubeLoadingAnimation()
            } else if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh is androidx.paging.LoadState.NotLoading) {
                YoutubeNoResultsAnimation()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    items(pagingItems.itemCount) { index ->
                        pagingItems[index]?.let { video ->
                            YoutubeSearchItem(
                                searchVideo = video,
                                onOpenPlayer = onOpenPlayer
                            )
                        }
                    }
                }
            }
        }
    }
}
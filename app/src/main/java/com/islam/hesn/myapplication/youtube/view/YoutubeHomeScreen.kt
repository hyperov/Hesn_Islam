package com.islam.hesn.myapplication.youtube.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel
import kotlinx.coroutines.launch
import ui.theme.ColorAccent
import ui.theme.ColorPrimary
import ui.theme.ColorPrimaryDark
import ui.theme.JanaFamily

@Composable
fun YoutubeHomeScreen(
    youtubeViewModel: YoutubeViewModel,
    youtubeSearchViewModel: YoutubeSearchViewModel,
    onOpenPlayer: (String, String) -> Unit,
    onOpenSearch: (String, Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val tabs = listOf(
        stringResource(id = R.string.main_channel),
        stringResource(id = R.string.education_channel),
    )

    val selectedTab = remember { mutableStateOf(0) }
    val searchText = remember { mutableStateOf("") }

    val mainChannelPlaylistId = stringResource(R.string.main_channel_playlist_id)
    val educationChannelPlaylistId = stringResource(R.string.education_channel_playlist_id)

    LaunchedEffect(selectedTab.value) {
        val playlistId = when (selectedTab.value) {
            0 -> mainChannelPlaylistId
            1 -> educationChannelPlaylistId
            else -> mainChannelPlaylistId
        }
        youtubeViewModel.getYoutubeChannelVideos(playlistId)
    }

    // Collect the flow from StateFlow and then collect as paging items
    val currentFlow by youtubeViewModel.flowState.collectAsStateWithLifecycle()
    val pagingItems = currentFlow.collectAsLazyPagingItems()
    val noResults = stringResource(R.string.no_results)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorPrimaryDark,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText.value,
                onValueChange = { searchText.value = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.search_title),
                        fontFamily = JanaFamily
                    )
                },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchText.value.isNotBlank()) {
                            youtubeSearchViewModel.updateSearchQuery(searchText.value)
                            youtubeSearchViewModel.updateSelectedTab(selectedTab.value)
                            onOpenSearch(searchText.value, selectedTab.value)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = noResults,
                                )
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = ColorAccent,
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ColorAccent,
                    unfocusedBorderColor = ColorAccent,
                    focusedTextColor = ColorPrimary,
                    unfocusedTextColor = ColorPrimary,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            )

            TabRow(
                selectedTabIndex = selectedTab.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                containerColor = ColorPrimary,
                contentColor = ColorAccent,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.value == index,
                        onClick = { selectedTab.value = index },
                        text = {
                            Text(
                                text = title,
                                fontFamily = JanaFamily,
                                color = ColorAccent,
                            )
                        },
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
            ) {
                if (pagingItems.loadState.refresh is androidx.paging.LoadState.Loading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(color = ColorAccent)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                    ) {
                        items(pagingItems.itemCount) { item ->
                            YoutubeVideoItem(
                                video = pagingItems[item]!!,
                                onOpenPlayer = onOpenPlayer
                            )
                        }
                    }
                }
            }
        }
    }
}





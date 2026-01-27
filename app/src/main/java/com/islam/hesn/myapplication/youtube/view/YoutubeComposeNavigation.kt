package com.islam.hesn.myapplication.youtube.view

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.islam.hesn.myapplication.youtube.view.search.YoutubeSearchScreen
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeSearchViewModel
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubeViewModel

object YoutubeDestinations {
    const val HOME = "youtube_home"
    const val PLAYER = "youtube_player"
    const val SEARCH = "youtube_search"

    const val ARG_VIDEO_ID = "videoId"
    const val ARG_VIDEO_TITLE = "videoTitle"
}

@Composable
fun YoutubeRootScreen(
    youtubeViewModel: YoutubeViewModel,
    youtubeSearchViewModel: YoutubeSearchViewModel,
    youtubePlayerViewModel: YoutubePlayerViewModel,
) {
    val navController = rememberNavController()

    val startDestination = remember { YoutubeDestinations.HOME }
    
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = YoutubeDestinations.HOME) {
            YoutubeHomeScreen(
                youtubeViewModel = youtubeViewModel,
                youtubeSearchViewModel = youtubeSearchViewModel,
                onOpenPlayer = { videoId, title ->
                    youtubePlayerViewModel.setVideo(videoId, title)
                    navController.navigate(
                        "${YoutubeDestinations.PLAYER}/$videoId"
                    )
                },
                onOpenSearch = { query, selectedTab ->
                    youtubeSearchViewModel.updateSearchQuery(query)
                    youtubeSearchViewModel.updateSelectedTab(selectedTab)
                    navController.navigate(YoutubeDestinations.SEARCH)
                }
            )
        }

        composable(
            route = "${YoutubeDestinations.PLAYER}/{${YoutubeDestinations.ARG_VIDEO_ID}}",
            arguments = listOf(
                navArgument(YoutubeDestinations.ARG_VIDEO_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            YoutubePlayerScreen(
                youtubePlayerViewModel = youtubePlayerViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = YoutubeDestinations.SEARCH) {
            YoutubeSearchScreen(
                youtubeSearchViewModel = youtubeSearchViewModel,
                youtubePlayerViewModel = youtubePlayerViewModel,
                onOpenPlayer = { videoId, title ->
                    youtubePlayerViewModel.setVideo(videoId, title)
                    navController.navigate(
                        "${YoutubeDestinations.PLAYER}/$videoId"
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}



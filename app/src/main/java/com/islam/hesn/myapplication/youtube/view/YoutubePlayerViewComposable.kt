package com.islam.hesn.myapplication.youtube.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.islam.hesn.myapplication.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerViewComposable(
    videoId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
//    val activity = LocalContext.current.findActivity()

//    var youTubePlayer by remember { mutableStateOf<YouTubePlayer?>(null) }
//
//    LaunchedEffect(youTubePlayer, videoId) {
//        youTubePlayer?.loadOrCueVideo(lifecycleOwner.lifecycle, videoId, 0f)
//    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = { ctx ->
//                YouTubePlayerView(ctx).apply {
//                    lifecycleOwner.lifecycle.addObserver(this)
//
//                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//                        override fun onReady(player: YouTubePlayer) {
//                            youTubePlayer = player
//                        }
//                    })
//                }
//            },
//            onRelease = { view ->
//                lifecycleOwner.lifecycle.removeObserver(view)
//                view.release()
//            }
//        )

        //iframe solution
        val lifecycleOwner = LocalLifecycleOwner.current
        var playbackPosition by rememberSaveable { mutableFloatStateOf(0f) }
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

            AndroidView(
                modifier = Modifier
                    .then(if (isLandscape) Modifier.fillMaxSize() else Modifier.fillMaxWidth())
                    .aspectRatio(16f / 9f)
                    .align(Alignment.Center),
                factory = { factoryContext ->
                    YouTubePlayerView(factoryContext).apply {
                        lifecycleOwner.lifecycle.addObserver(this)

                        // ✅ Disable auto init
                        enableAutomaticInitialization = false

                        // ✅ Configure IFrame options explicitly
                        val options = IFramePlayerOptions.Builder(factoryContext)
                            .controls(1)      // Show native controls
                            .rel(0)           // No related videos at the end
                            .ivLoadPolicy(3)  // Annotations behavior
                            .ccLoadPolicy(0)  // Captions off by default
                            .build()

                        initialize(object : AbstractYouTubePlayerListener() {
                            override fun onReady(player: YouTubePlayer) {
                                // Single, deterministic load
                                player.loadVideo(videoId, playbackPosition)
                            }

                            override fun onCurrentSecond(player: YouTubePlayer, second: Float) {
                                playbackPosition = second
                            }
                        }, options)
                    }
                }
            )

        Icon(
//            imageVector = Icons.Filled.Close,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_youtube),
            contentDescription = "Close player",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(40.dp)
                .clickable { onBack() }
        )
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

package com.islam.hesn.myapplication.youtube.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.islam.hesn.myapplication.youtube.model.response.Video
import ui.theme.ColorAccent
import ui.theme.JanaFamily

@Composable
fun YoutubeVideoItem(
    video: Video,
    onOpenPlayer: (String, String) -> Unit,
) {
    val snippet = video.snippet
    val thumbnails = snippet.thumbnails
    val thumbnailUrl =
        thumbnails.standard?.url
            ?: thumbnails.high?.url
            ?: thumbnails.medium?.url
            ?: thumbnails.default?.url

    Box(contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val id = snippet.resourceId?.videoId.orEmpty()
                if (id.isNotEmpty()) {
                    onOpenPlayer(id, snippet.title)
                }
            },
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = snippet.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.7f)
                .background(color = Black)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = snippet.title,
                style = MaterialTheme.typography.titleMedium,
                color = White,
                fontFamily = JanaFamily,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
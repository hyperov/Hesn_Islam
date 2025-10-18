package com.islam.hesn.myapplication.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.islam.hesn.myapplication.R
import io.opencensus.trace.Span

@Composable
fun MoreScreen(modifier: Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = modifier,
                elevation = 4.dp,
                shape = MaterialTheme.shapes.small,
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Column {
                    ImageVector.vectorResource(id = R.drawable.ic_more)
                    Spacer(modifier)
                    Text(text = stringResource(R.string.more))
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewMoreScreen() {
    MoreScreen(Modifier)
}
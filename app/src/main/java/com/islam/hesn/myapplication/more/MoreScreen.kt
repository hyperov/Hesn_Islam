package com.islam.hesn.myapplication.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.viewmodel.QuranViewModel
import com.islam.hesn.myapplication.utils.BOOKMARK_AYA_NUMBER
import com.islam.hesn.myapplication.utils.BOOKMARK_SURAH_NUMBER
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.youtube.viewmodel.YoutubePlayerViewModel
import kotlinx.coroutines.launch
import ui.components.IconAndTextCard
import ui.theme.ColorAccent
import ui.theme.ColorPrimary
import ui.theme.ColorPrimaryDark
import ui.theme.JanaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    modifier: Modifier,
    youtubePlayerViewModel: YoutubePlayerViewModel = viewModel(),
    quranViewModel: QuranViewModel = viewModel(),
    navigateToSurah: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val noBookmarksMessage = stringResource(R.string.no_bookmarks)

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.more),
                            fontFamily = JanaFamily,
                            fontSize = 24.sp,
                            color = ColorAccent,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = modifier.width(16.dp))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more),
                            contentDescription = null,
                            tint = ColorAccent
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorPrimary,
                    titleContentColor = ColorAccent,
                )
            )

        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = ColorPrimaryDark),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconAndTextCard(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark2),
                textResource = stringResource(R.string.last_read),
                onClick = {
                    if (Prefs.contains(BOOKMARK_SURAH_NUMBER) && Prefs.contains(BOOKMARK_AYA_NUMBER)) {
                        quranViewModel.isBookMark.value = true
                        if (quranViewModel.ayat.value.isNullOrEmpty())
                            quranViewModel.getAllArabicSurah()
                        //value isn't important..but only to activate observer in surah fragment
                        quranViewModel.surahId.value = quranViewModel.surahId.value
                        navigateToSurah.invoke()
//                        findNavController().navigate(R.id.surahFragment)
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = noBookmarksMessage,
                                withDismissAction = true
                            )
                        }

                    }
                })
            IconAndTextCard(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_information),
                textResource = stringResource(R.string.about_us),
                onClick = {})
            IconAndTextCard(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_contact5),
                textResource = stringResource(R.string.contact_us),
                onClick = {})
        }
    }
}


@Preview
@Composable
fun PreviewMoreScreen() {
    MoreScreen(
        modifier = Modifier,
        youtubePlayerViewModel = viewModel(),
        quranViewModel = viewModel(),
        navigateToSurah = {}
    )
}
package com.islam.hesn.myapplication.pdfbook

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.islam.hesn.myapplication.R
import ui.theme.ColorAccent
import ui.theme.ColorPrimary
import ui.theme.JanaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfScreen(
    modifier: Modifier = Modifier,
    pdfAssetName: String = "hesn_islam.pdf",
    defaultPage: Int = 2,
    onNavigateBack: (() -> Unit)? = null
) {
    // Force RTL layout direction like the Activity does
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.pdf_book_name),
                                fontFamily = JanaFamily,
                                fontSize = 24.sp,
                                color = ColorAccent,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    navigationIcon = {
                        if (onNavigateBack != null) {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
//                                    painter = painterResource(id = android.R.drawable.ic_arrow_back),
                                    painter = painterResource(id = android.R.drawable.ic_menu_day),
                                    contentDescription = "Back",
                                    tint = ColorAccent
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = ColorPrimary,
                        titleContentColor = ColorAccent,
                        navigationIconContentColor = ColorAccent
                    )
                )
            }
        ) { innerPadding ->
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                factory = { ctx ->
                    PDFView(ctx, null).apply {
                        fromAsset(pdfAssetName)
                            .defaultPage(defaultPage)
                            .enableSwipe(true) // allows to block changing pages using swipe
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                            .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
                            .pageFling(true) // make a fling change only a single page like ViewPager
                            .load()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PdfScreenPreview() {
    PdfScreen()
}


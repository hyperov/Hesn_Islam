package com.islam.hesn.myapplication.misconceptions.site

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.crashlytics.FirebaseCrashlytics
import ui.theme.ColorPrimaryDark

@Composable
fun MisconceptionsSiteScreen(
    modifier: Modifier = Modifier,
    url: String = "https://hosenalislam.com/"
) {
    var isLoading by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MisconceptionsSiteScreen")
        onDispose { }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // WebView
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        @Deprecated("Deprecated in Java")
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            return false
                        }

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
//                    loadUrl(url)
                }
            }, update = {
                it.loadUrl(url)
            }
        )

        // Lottie Loading Animation
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorPrimaryDark),
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.Asset("loading_dots.json")
                )

                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    speed = 1.5f,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisconceptionsSiteScreenPreview() {
    MisconceptionsSiteScreen()
}


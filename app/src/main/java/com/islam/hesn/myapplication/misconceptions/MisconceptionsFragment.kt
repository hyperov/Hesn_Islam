package com.islam.hesn.myapplication.misconceptions

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.databinding.FragmentMisconceptionsBinding


class MisconceptionsFragment : Fragment() {

    private var _binding: FragmentMisconceptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMisconceptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MisconceptionsFragment")

        val webView = binding.webView
        val webSettings = webView.settings

        webSettings.javaScriptEnabled = true
        webView.apply {
            webViewClient = MyWebViewClient(binding.progressMisconceptions)
            loadUrl("https://hosenalislam.com/")
        }
    }

    class MyWebViewClient(private val progress: LottieAnimationView) : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress.isVisible = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progress.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
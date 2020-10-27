package com.islam.hesn.myapplication.misconceptions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.islam.hesn.myapplication.R
import kotlinx.android.synthetic.main.fragment_misconceptions.*


class MisconceptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_misconceptions, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.apply {
            webViewClient = MyWebViewClient()
            loadUrl("http://rodood.win/")
        }
    }

    class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            return false
        }
    }

//    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()) {
//            this.webView.goBack()
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

}
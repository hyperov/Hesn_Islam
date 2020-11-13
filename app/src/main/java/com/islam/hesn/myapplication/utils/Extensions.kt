package com.islam.hesn.myapplication.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment


fun Fragment.openYoutubeChannelIntent(channelId: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data =
        Uri.parse("https://www.youtube.com/channel/$channelId")
    try {
        intent.setPackage("com.google.android.youtube")
    } catch (e: ActivityNotFoundException) {
    } finally {
        startActivity(intent)
    }
}
package com.islam.hesn.myapplication.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.islam.hesn.myapplication.R

lateinit var Prefs: SharedPreferences

const val BOOKMARK_SURAH_NUMBER = "BOOKMARK_SURAH_NUMBER"
const val BOOKMARK_SURAH_NAME = "BOOKMARK_SURAH_NAME"
const val BOOKMARK_AYA_NUMBER = "BOOKMARK_AYA_NUMBER"

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

// add entry in shared preference
fun SharedPreferences.putAny(name: String, any: Any) {
    when (any) {
        is String -> edit().putString(name, any).apply()
        is Int -> edit().putInt(name, any).apply()
        is Boolean -> edit().putBoolean(name, any).apply()

    }
}

fun Context.showSnackBar(view: View, navigation: BottomNavigationView, text: String) {
    Snackbar.make(view,
        text,
        Snackbar.LENGTH_LONG)
        .setTextColor(ContextCompat.getColor(
            this,
            R.color.colorAccent))
        .setActionTextColor(ContextCompat.getColor(
            this,
            R.color.colorAccent))
        .apply {
            setAction(getString(R.string.dismiss)) { dismiss() }.show()

            anchorView = navigation
        }
}

fun Context.showSnackBar(view: View, navigation: BottomNavigationView, text: String,@ColorRes textColor:Int) {
    Snackbar.make(view,
        text,
        Snackbar.LENGTH_LONG)
        .setTextColor(ContextCompat.getColor(
            this,
            textColor))
        .setActionTextColor(ContextCompat.getColor(
            this,
            R.color.colorAccent))
        .apply {
            setAction(getString(R.string.dismiss)) { dismiss() }.show()

            anchorView = navigation
        }
}
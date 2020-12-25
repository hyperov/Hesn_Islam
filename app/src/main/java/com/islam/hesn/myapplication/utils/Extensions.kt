package com.islam.hesn.myapplication.utils

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.net.Uri
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.islam.hesn.myapplication.R
import java.io.IOException
import java.nio.charset.Charset

lateinit var Prefs: SharedPreferences

const val BOOKMARK_SURAH_NUMBER = "BOOKMARK_SURAH_NUMBER"
const val BOOKMARK_SURAH_NAME = "BOOKMARK_SURAH_NAME"
const val BOOKMARK_AYA_NUMBER = "BOOKMARK_AYA_NUMBER"

const val arabicFile = "arabic_quran.json"
const val IS_CONNECTED = "IS_CONNECTED"
const val COUNTER_FOR_REVIEW = "COUNTER_FOR_REVIEW"

const val MAX_COUNT_REVIEW_DIALOG_SHOW = 10

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

fun Context.showSnackBar(
    view: View,
    navigation: BottomNavigationView,
    text: String,
    @ColorRes textColor: Int,
) {
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


fun AssetManager.readJsonStringFromAssets(fileName: String): String? {
    val json: String?
    val charset: Charset = Charsets.UTF_8
    try {
        val `is` = open(fileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        json = String(buffer, charset)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun Fragment.changeToolbarTitle(text: String) {
    val toolbar = activity?.findViewById<MaterialToolbar>(R.id.toolbar)

    toolbar?.title = text
}

fun Fragment.createDialog(title: String, message: String) {
    MaterialAlertDialogBuilder(this.requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("ok") { dialog, _ -> dialog.dismiss() }
        .show()
}

fun registerNetworkConnectionEvents(context: Application) {
    val cm: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val builder: NetworkRequest.Builder = NetworkRequest.Builder()

    cm.registerNetworkCallback(
        builder.build(),
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                Prefs.putAny(IS_CONNECTED, true)
            }

            override fun onLost(network: Network) {
                Prefs.putAny(IS_CONNECTED, false)
            }
        })
}
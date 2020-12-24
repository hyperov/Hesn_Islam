package com.islam.hesn.myapplication.home

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.putAny
import java.io.IOException
import java.nio.charset.Charset

const val arabicFile = "arabic_quran.json"
const val IS_CONNECTED = "IS_CONNECTED"

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
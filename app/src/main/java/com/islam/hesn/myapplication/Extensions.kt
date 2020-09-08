package com.islam.hesn.myapplication

import android.content.res.AssetManager
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.nio.charset.Charset

const val arabicFile = "arabic_quran.json"

fun AssetManager.readJsonStringFromAssets(fileName: String): String? {
    var json: String? = null
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

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction("dismiss") { (it as Snackbar).dismiss() }
        .setActionTextColor(ContextCompat.getColor(context, R.color.design_default_color_error))
        .show()
}
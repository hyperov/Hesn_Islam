package com.islam.hesn.myapplication.home

import android.content.res.AssetManager
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.islam.hesn.myapplication.R
import java.io.IOException
import java.nio.charset.Charset

const val arabicFile = "arabic_quran.json"

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
//        .setPositiveButtonIcon(ContextCompat.getDrawable(this.requireContext(),R.drawable.ic_book))
        .show()
}
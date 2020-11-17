package com.islam.hesn.myapplication.app

import android.app.Application
import androidx.preference.PreferenceManager
import com.islam.hesn.myapplication.utils.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Prefs = PreferenceManager.getDefaultSharedPreferences(this)
    }
}
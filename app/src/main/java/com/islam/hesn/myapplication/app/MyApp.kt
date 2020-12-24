package com.islam.hesn.myapplication.app

import android.app.Application
import androidx.preference.PreferenceManager
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.islam.hesn.myapplication.BuildConfig
import com.islam.hesn.myapplication.home.registerNetworkConnectionEvents
import com.islam.hesn.myapplication.utils.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
        }
        Prefs = PreferenceManager.getDefaultSharedPreferences(this)
        registerNetworkConnectionEvents(this)
    }
}
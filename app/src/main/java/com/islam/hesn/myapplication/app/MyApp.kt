package com.islam.hesn.myapplication.app

import android.app.Application
import androidx.preference.PreferenceManager
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.islam.hesn.myapplication.BuildConfig
import com.islam.hesn.myapplication.utils.COUNTER_FOR_REVIEW
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.putAny
import com.islam.hesn.myapplication.utils.registerNetworkConnectionEvents
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (!BuildConfig.DEBUG) {
            Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
        }
        Prefs = PreferenceManager.getDefaultSharedPreferences(this)
        Prefs.putAny(COUNTER_FOR_REVIEW, Prefs.getInt(COUNTER_FOR_REVIEW, 0))
        registerNetworkConnectionEvents(this)
    }
}
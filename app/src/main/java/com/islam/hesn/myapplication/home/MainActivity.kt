package com.islam.hesn.myapplication.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.utils.COUNTER_FOR_REVIEW
import com.islam.hesn.myapplication.utils.MAX_COUNT_REVIEW_DIALOG_SHOW
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.putAny
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*

const val MY_REQUEST_CODE: Int = 111

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var appUpdateInfoTask: Task<AppUpdateInfo>
    private lateinit var appUpdateManager: AppUpdateManager

    private lateinit var appBarConfiguration: AppBarConfiguration

    var reviewInfo: ReviewInfo? = null
    lateinit var manager: ReviewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initReviews()
        initUpdates()
        setSupportActionBar(toolbar)
        if (window.decorView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    private fun initUpdates() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(this)

        // Returns an intent object that you use to check for an update.
        appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        checkForUpdates()
    }

    private fun checkForUpdates() {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // For a flexible update, use AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                FirebaseCrashlytics.getInstance().setCustomKey("UPDATE_API_INIT", true)
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE)
            }
        }
    }

    private fun resumeUpdate(){

            appUpdateInfoTask
            .addOnSuccessListener { appUpdateInfo ->

                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    FirebaseCrashlytics.getInstance().setCustomKey("UPDATE_API_RESUME_DOWNLOAD", true)
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    );
                }
            }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.quranFragment,
                R.id.misconceptionsMainFragment,
                R.id.youtubeFragment,
                R.id.bibleFragment,
                R.id.moreFragment
            )
        )

        setupActionBarWithNavController(this, navController, appBarConfiguration)
        // Setting Navigation Controller with the BottomNavigationView
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        resumeUpdate()
        if (Prefs.getInt(COUNTER_FOR_REVIEW, 0) >= MAX_COUNT_REVIEW_DIALOG_SHOW)
            askForReview(reviewInfo, manager)
    }

    private fun initReviews() {

        manager = ReviewManagerFactory.create(this)
        manager.requestReviewFlow().addOnCompleteListener { request ->
            if (request.isSuccessful) {
                reviewInfo = request.result
                FirebaseCrashlytics.getInstance().setCustomKey("REVIEWS_API_INIT", true)

            } else {
                FirebaseCrashlytics.getInstance().setCustomKey("REVIEWS_API_INIT", false)

            }
        }
    }


    // Call this when you want to show the dialog
    private fun askForReview(reviewInfo: ReviewInfo?, manager: ReviewManager) {
        if (reviewInfo != null) {
            manager.launchReviewFlow(this, reviewInfo).addOnFailureListener {

                // Log error and continue with the flow
                FirebaseCrashlytics.getInstance().setCustomKey("REVIEWS_API_DIALOG_SHOW", false)

            }.addOnCompleteListener { _ ->

                FirebaseCrashlytics.getInstance().setCustomKey("REVIEWS_API_DIALOG_SHOW", true)
                Prefs.putAny(COUNTER_FOR_REVIEW, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            when(resultCode) {
                Activity.RESULT_CANCELED -> {
                    FirebaseCrashlytics.getInstance().setCustomKey("UPDATE_API_DOWNLOAD_CANCELED", "UPDATE_API_DIALOG_CANCELED")
                    checkForUpdates()
                }
                Activity.RESULT_OK -> {
                    Log.d(this::class.simpleName,"Update Success! Result code: $resultCode")
                    FirebaseCrashlytics.getInstance().setCustomKey("UPDATE_API_DOWNLOAD_SUCCESS", "UPDATE_API_DOWNLOAD_SUCCESS")
                }
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED->{
                    Log.d(this::class.simpleName,"Update flow failed! Result code: $resultCode")
                    FirebaseCrashlytics.getInstance().setCustomKey("UPDATE_API_DOWNLOAD_FAILED", "UPDATE_API_DOWNLOAD_FAILED")
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    // Request the update.
                    checkForUpdates()
                }
            }
        }
    }

}
package com.islam.hesn.myapplication.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.utils.COUNTER_FOR_REVIEW
import com.islam.hesn.myapplication.utils.MAX_COUNT_REVIEW_DIALOG_SHOW
import com.islam.hesn.myapplication.utils.Prefs
import com.islam.hesn.myapplication.utils.putAny
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    var reviewInfo: ReviewInfo? = null
    lateinit var manager: ReviewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initReviews()
        setSupportActionBar(toolbar)
        if (window.decorView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
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
                R.id.misconceptionsFragment,
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

}
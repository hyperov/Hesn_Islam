package com.islam.hesn.myapplication

import android.content.Intent
import android.os.Bundle
import com.codemybrainsout.onboarder.AhoyOnboarderActivity
import com.codemybrainsout.onboarder.AhoyOnboarderCard
import com.islam.hesn.myapplication.home.MainActivity


class TutorialActivity : AhoyOnboarderActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createCard()
    }

    private fun createCard() {
        val card1 = AhoyOnboarderCard("Title", "Description", R.drawable.ic_telephone)
        card1.setBackgroundColor(R.color.black_transparent)
        card1.setTitleColor(R.color.colorAccent)
        card1.setDescriptionColor(R.color.grey_200)
        card1.setTitleTextSize(dpToPixels(10, this))
        card1.setDescriptionTextSize(dpToPixels(8, this))
        card1.setIconLayoutParams(dpToPixels(128, this).toInt(),
            dpToPixels(128, this).toInt(),
            dpToPixels(128, this).toInt(),
            dpToPixels(16, this).toInt(),
            dpToPixels(16, this).toInt(),
            dpToPixels(16, this).toInt())

        val pages = ArrayList<AhoyOnboarderCard>()
        pages.add(card1);
        pages.add(card1);
        pages.add(card1);

        setGradientBackground()
        setOnboardPages(pages)

    }

    override fun onFinishButtonPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
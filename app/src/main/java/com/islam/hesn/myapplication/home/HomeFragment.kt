package com.islam.hesn.myapplication.home

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.hypot

class HomeFragment : Fragment(), View.OnClickListener {

    private var centerY: Float = 0F
    private var centerX: Float = 0F
    private var radius: Float = 0F

    private lateinit var createCircularRevealAnim: Animator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeToolbarTitle(getString(R.string.app_name))
        main.post {
            setupAnim()
            enterCircularTransition()
        }
        for (v: View in main.children)
            v.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupAnim() {
        centerX = (main.measuredWidth / 2).toFloat()
        centerY = (main.measuredHeight / 2).toFloat()

        // get the initial radius for the clipping circle
        radius = (hypot(main.width.toDouble(), main.height.toDouble()) / 2).toFloat()
    }

    private fun enterCircularTransition() {
        createCircularRevealAnim = ViewAnimationUtils.createCircularReveal(
            main,
            centerX.toInt(),
            centerY.toInt(),
            0f,
            radius
        )

        createCircularRevealAnim.apply {
            duration = 700
            interpolator = LinearOutSlowInInterpolator()
//            grid.visibility = View.VISIBLE
            start()
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            quran -> {
                findNavController().navigate(R.id.quranFragment)
            }
            bible -> {
                findNavController().navigate(R.id.bibleFragment)
            }
        }
    }

//    fun exitCircularTransition() {
//        // previously visible view
//
//        // create the animation (the final radius is zero)
//        val anim =
//            ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius.toFloat(), 0f)
//
//        // make the view invisible when the animation is done
//        anim.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                super.onAnimationEnd(animation)
//                myView.visibility = View.INVISIBLE
//            }
//        })
//
//        // start the animation
//        anim.start()
//    }
}
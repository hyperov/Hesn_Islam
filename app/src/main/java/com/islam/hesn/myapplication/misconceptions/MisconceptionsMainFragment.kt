package com.islam.hesn.myapplication.misconceptions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import kotlinx.android.synthetic.main.fragment_misconceptions_main.*


class MisconceptionsMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_misconceptions_main, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MisconceptionsMainFragment")
        cvMisconceptions.setOnClickListener { findNavController().navigate(R.id.misconceptionsFragment) }
        cvReligions.setOnClickListener { findNavController().navigate(R.id.misconceptionsSitesFragment) }
    }

}
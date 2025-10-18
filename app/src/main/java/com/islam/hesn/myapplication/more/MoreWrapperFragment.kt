package com.islam.hesn.myapplication.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreWrapperFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MoreWrapperFragment")
        MoreScreen(
            navigateToSurah = {
                findNavController().navigate(R.id.surahFragment)
            }, modifier = Modifier
        )
    }

}
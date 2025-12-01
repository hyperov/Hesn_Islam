package com.islam.hesn.myapplication.misconceptions.site

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.misconceptions.MisconceptionsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MisconceptionsSiteWrapperFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {
        MisconceptionsSiteScreen()
    }

}
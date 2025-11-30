package com.islam.hesn.myapplication.misconceptions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.pdfbook.PdfActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MisconceptionsWrapperFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {
        MisconceptionsScreen(
            navigateToMisconceptions = {
                findNavController().navigate(R.id.misconceptionsFragment)
            },
            modifier = Modifier,
            navigateToPdf = {
                startActivity(
                    Intent(requireContext(), PdfActivity::class.java)
                )
            },

            )
    }

}
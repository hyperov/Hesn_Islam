package com.islam.hesn.myapplication.misconceptions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.pdfbook.PdfScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MisconceptionsWrapperFragment : Fragment() {

    @Serializable
    object Misconceptions

    @Serializable
    object PDF

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {


        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Misconceptions
        ) {

            composable<Misconceptions> {
                MisconceptionsScreen(
                    navigateToMisconceptions = {
                        findNavController().navigate(R.id.misconceptionsFragment)
                    },
                    modifier = Modifier,
                    navigateToPdf = {
                        navController.navigate(PDF)
                    },

                    )
            }

            composable<PDF> {
                PdfScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}
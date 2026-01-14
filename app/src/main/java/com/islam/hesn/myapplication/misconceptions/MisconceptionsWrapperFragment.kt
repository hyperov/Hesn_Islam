package com.islam.hesn.myapplication.misconceptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.islam.hesn.myapplication.misconceptions.site.MisconceptionsSiteScreen
import com.islam.hesn.myapplication.misconceptions.pdfbook.PdfScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MisconceptionsWrapperFragment : Fragment() {

    @Serializable
    object Misconceptions

    @Serializable
    object PDF

    @Serializable
    object MisconceptionsSite

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
                        navController.navigate(MisconceptionsSite)
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

            composable<MisconceptionsSite> { MisconceptionsSiteScreen() }
        }

    }
}
package com.islam.hesn.myapplication.misconceptions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.FragmentMisconceptionsMainBinding
import com.islam.hesn.myapplication.pdfbook.PdfActivity


class MisconceptionsMainFragment : Fragment() {

    private var _binding: FragmentMisconceptionsMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMisconceptionsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().setCustomKey("SCREEN", "MisconceptionsMainFragment")
        binding.apply {
            cvMisconceptions.setOnClickListener { findNavController().navigate(R.id.misconceptionsFragment) }
            cvPdfBook.setOnClickListener {
                startActivity(
                    Intent(requireContext(), PdfActivity::class.java)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
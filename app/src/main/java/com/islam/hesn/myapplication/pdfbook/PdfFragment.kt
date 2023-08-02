package com.islam.hesn.myapplication.pdfbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.islam.hesn.myapplication.databinding.FragmentPdfBinding


class PdfFragment : Fragment() {

    private var _binding: FragmentPdfBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPdfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPdf()
    }

    private fun loadPdf() {
        binding.pdfView.fromAsset("hesn_islam.pdf")
            .defaultPage(2)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
            .pageFling(true) // make a fling change only a single page like ViewPager
            .load()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
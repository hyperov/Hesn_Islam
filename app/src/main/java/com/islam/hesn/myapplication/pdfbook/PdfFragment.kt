package com.islam.hesn.myapplication.pdfbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.util.FitPolicy
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
//            .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            // allows to draw something on the current page, usually visible in the middle of the screen
//            .onDraw(onDrawListener)
//            // allows to draw something on all pages, separately for every page. Called only for visible pages
//            .onDrawAll(onDrawListener)
                //loading false
//            .onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
//            .onPageChange(onPageChangeListener)
//            .onPageScroll(onPageScrollListener)
            //loading false
//            .onError(onErrorListener)
            //loading false
//            .onPageError(onPageErrorListener)
            //loading false
//            .onRender(onRenderListener) // called after document is rendered for the first time
            // called on single tap, return true if handled, false to toggle scroll handle visibility
//            .onTap(onTapListener)
//            .onLongPress(onLongPressListener)
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
            // spacing between pages in dp. To define spacing color, set view background
            .spacing(0)
//            .linkHandler(DefaultLinkHandler)
            .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
            .fitEachPage(true) // fit each page to the view, else smaller pages are scaled relative to largest page.
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
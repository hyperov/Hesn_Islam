package com.islam.hesn.myapplication.pdfbook

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.ActivityPdfBinding

class PdfActivity : AppCompatActivity() {

    private var _binding: ActivityPdfBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include2.toolbar)
        supportActionBar?.title = getString(R.string.pdf_book_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (window.decorView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.islam.hesn.myapplication.search.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.model.response.arabic.SurahItem
import com.islam.hesn.myapplication.quran.view.SEARCH_QUERY
import com.islam.hesn.myapplication.search.model.SearchExpandableAdapter

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        handleNewIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNewIntent(intent!!)
    }

    private fun handleNewIntent(intent: Intent) {

        intent.getStringExtra(SEARCH_QUERY)?.let { query ->
            doMySearch(query)
        }

    }

    private fun doMySearch(query: String) {
        val adapter = SearchExpandableAdapter<SurahItem>(arrayListOf())
    }
}
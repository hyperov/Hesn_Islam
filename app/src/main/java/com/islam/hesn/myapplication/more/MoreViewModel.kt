package com.islam.hesn.myapplication.more

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.islam.hesn.myapplication.quran.model.repo.arabic.QuranRepo

class MoreViewModel @ViewModelInject constructor(
    private val quranRepo: QuranRepo,
) : ViewModel() {

}
package com.islam.hesn.myapplication.search.model


import android.view.View
import android.widget.TextView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.quran.model.response.arabic.SurahItem

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder


class ItemViewHolder<T>(itemView: View) : ChildViewHolder(itemView) {

    private val itemName: TextView = itemView.findViewById(R.id.content)
    private val itemNum: TextView = itemView.findViewById(R.id.item_num)

    fun setVerse(verse: T) {

        if (verse is Verse) {
            itemName.text = verse.verseContent
            itemNum.text = verse.verseNum.toString()
        }
        if (verse is SurahItem) {
            itemName.text = verse.standard_full
            itemNum.text = verse.aya_id.toString()
        }

    }

}
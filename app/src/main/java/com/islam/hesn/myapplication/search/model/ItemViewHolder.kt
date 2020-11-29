package com.islam.hesn.myapplication.search.model


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.islam.hesn.myapplication.utils.BOOKMARK_AYA_NUMBER
import com.islam.hesn.myapplication.utils.Prefs
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder


class ItemViewHolder<T>(itemView: View) : ChildViewHolder(itemView) {

    private val itemName: TextView = itemView.findViewById(R.id.content)
    private val itemNum: TextView = itemView.findViewById(R.id.item_num)
    private val ivLastRead: ImageView = itemView.findViewById(R.id.ivLastRead)

    fun setVerse(
        verse: T,
        searchVerseItemClick: ((verse: Verse) -> Unit)? = null,
        searchAyaItemClick: ((verse: AyaItem) -> Unit)? = null,
        isFromQuran: Boolean,
        onLastReadClick: ((surahId: Int, ayaId: Int, surahName: String) -> Unit)?,
        searchExpandableAdapter: SearchExpandableAdapter<AyaItem>,
    ) {

        if (verse is Verse) {
            itemName.text = verse.verseContent
            itemNum.text = verse.verseNum.toString()
        }
        if (verse is AyaItem) {
            itemName.text = verse.standard_full
            itemNum.text = verse.aya_id.toString()
        }

        if (isFromQuran) {
            val verseItem = verse as AyaItem
            ivLastRead.setOnClickListener {

                onLastReadClick?.invoke(verseItem.sura_id, verseItem.aya_id, verseItem.sura_name)
                searchExpandableAdapter.notifyDataSetChanged()

            }

            if (Prefs.getInt(BOOKMARK_AYA_NUMBER, 1) == verseItem.aya_id)
                ivLastRead.setImageDrawable(ResourcesCompat.getDrawable(itemView.context.resources,
                    R.drawable.ic_starred,
                    null))
            else
                ivLastRead.setImageDrawable(ResourcesCompat.getDrawable(itemView.context.resources,
                    R.drawable.ic_unstarred,
                    null))
        }

        itemView.setOnClickListener {
            if (verse is Verse) {
                searchVerseItemClick?.invoke(verse)
            } else if (verse is AyaItem) {
                searchAyaItemClick?.invoke(verse)
            }
        }

    }

}
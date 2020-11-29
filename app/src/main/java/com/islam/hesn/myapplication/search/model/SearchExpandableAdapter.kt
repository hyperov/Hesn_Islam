package com.islam.hesn.myapplication.search.model

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


class SearchExpandableAdapter<T : Parcelable>(
    groups: List<ExpandableGroup<T>>,
    val isFromQuran: Boolean,
    val searchVerseItemClick: ((verse: Verse) -> Unit)? = null,
    val searchAyaItemClick: ((verse: AyaItem) -> Unit)? = null,
    val onLastReadClick: ((surahId: Int, ayaId: Int,surahName:String) -> Unit)? = null,
) :
    ExpandableRecyclerViewAdapter<GroupViewHolder, ItemViewHolder<T>>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout_section_quran, parent, false)
        return GroupViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        val view: View = if (isFromQuran)
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_surah, parent, false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_chapter, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindChildViewHolder(
        holder: ItemViewHolder<T>,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int,
    ) {
        val verse = group.items[childIndex]
        holder.setVerse(verse as T, searchVerseItemClick, searchAyaItemClick,isFromQuran,onLastReadClick,this as SearchExpandableAdapter<AyaItem>)

    }

    override fun onBindGroupViewHolder(
        holder: GroupViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
    ) =
        holder.setSectionTitle(group!!)


}
package com.islam.hesn.myapplication.search.model

import android.view.View
import android.widget.TextView
import com.islam.hesn.myapplication.R
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class GroupViewHolder(itemView: View) : GroupViewHolder(itemView) {

    private val genreTitle: TextView = itemView.findViewById(R.id.tvSection)

    fun setSectionTitle(group: ExpandableGroup<*>) {
        genreTitle.text = group.title
    }

}
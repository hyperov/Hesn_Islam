package com.islam.hesn.myapplication.search.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.islam.hesn.myapplication.R
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class GroupViewHolder(itemView: View) : GroupViewHolder(itemView) {

    private val genreTitle: TextView = itemView.findViewById(R.id.tvSection)
    private val ivArrow: ImageView = itemView.findViewById(R.id.ivArrow)

    fun setSectionTitle(group: ExpandableGroup<*>, isCollapsed: Boolean) {
        genreTitle.text = group.title
        if (isCollapsed)
            ivArrow.setImageResource(R.drawable.ic_arrow_drop_down)
        else
            ivArrow.setImageResource(R.drawable.ic_arrow_left)
    }

}
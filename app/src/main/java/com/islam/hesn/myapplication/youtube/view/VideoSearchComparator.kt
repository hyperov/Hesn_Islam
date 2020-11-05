package com.islam.hesn.myapplication.youtube.view

import androidx.recyclerview.widget.DiffUtil
import com.islam.hesn.myapplication.youtube.model.response.SearchVideo

object VideoSearchComparator : DiffUtil.ItemCallback<SearchVideo>() {

    override fun areItemsTheSame(oldItem: SearchVideo, newItem: SearchVideo)
    // Id is unique.
            =
        oldItem.id.videoId == newItem.id.videoId


    override fun areContentsTheSame(oldItem: SearchVideo, newItem: SearchVideo): Boolean {
        return oldItem == newItem
    }
}
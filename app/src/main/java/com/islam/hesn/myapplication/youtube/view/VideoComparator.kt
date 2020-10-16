package com.islam.hesn.myapplication.youtube.view

import androidx.recyclerview.widget.DiffUtil
import com.islam.hesn.myapplication.youtube.model.response.Video

object VideoComparator : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}
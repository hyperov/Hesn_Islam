package com.islam.hesn.myapplication.youtube.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.model.response.SearchVideo
import com.islam.hesn.myapplication.youtube.view.YoutubeRecyclerViewSearchPagingAdapter.YoutubeViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout_youtube_first_channel.view.*

class YoutubeRecyclerViewSearchPagingAdapter(
    diffCallback: DiffUtil.ItemCallback<SearchVideo>,
    private val onVideoClick: ((videoId: String, videoTitle: String) -> Unit),
) :
    PagingDataAdapter<SearchVideo, YoutubeViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_youtube_first_channel, parent, false)
        return YoutubeViewHolder(view)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount == 0) R.layout.item_layout_youtube_first_channel
        else super.getItemViewType(position)
    }


    inner class YoutubeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(video: SearchVideo) = with(itemView) {
            with(video.snippet) {
                tvName.text = title
                with(thumbnails) {
                    standard?.let {
                        Picasso.get().load(standard.url).into(ivThumbnail)
                        return@with
                    }
                    high?.let {
                        Picasso.get().load(high.url).into(ivThumbnail)
                        return@with
                    }
                    medium?.let {
                        Picasso.get().load(medium.url).into(ivThumbnail)
                        return@with
                    }
                    default?.let {
                        Picasso.get().load(default.url).into(ivThumbnail)
                        return@with
                    }
                }
                video.apply {

                    setOnClickListener { onVideoClick((video as SearchVideo).id.videoId, title) }
                }


            }
        }

    }
}
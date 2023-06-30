package com.islam.hesn.myapplication.youtube.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.databinding.ItemLayoutYoutubeFirstChannelBinding
import com.islam.hesn.myapplication.youtube.model.response.CommonVideo
import com.islam.hesn.myapplication.youtube.model.response.Video
import com.islam.hesn.myapplication.youtube.view.YoutubeRecyclerViewPagingAdapter.YoutubeViewHolder
import com.squareup.picasso.Picasso

class YoutubeRecyclerViewPagingAdapter(
    diffCallback: DiffUtil.ItemCallback<Video>,
    private val onVideoClick: ((videoId: String, videoTitle: String) -> Unit),
) :
    PagingDataAdapter<Video, YoutubeViewHolder>(diffCallback) {

    private var _binding: ItemLayoutYoutubeFirstChannelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {

        _binding = ItemLayoutYoutubeFirstChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return YoutubeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    inner class YoutubeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(video: CommonVideo) = with(itemView) {
            with(video.snippet) {

                binding.tvName.text = title
                val ivThumbnail = binding.ivThumbnail

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
                video.snippet.apply {
                    setOnClickListener { onVideoClick(resourceId!!.videoId, title) }
                }


            }
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _binding = null
    }
}
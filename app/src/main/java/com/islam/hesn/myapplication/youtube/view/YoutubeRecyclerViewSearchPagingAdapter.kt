package com.islam.hesn.myapplication.youtube.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.databinding.ItemLayoutYoutubeFirstChannelBinding
import com.islam.hesn.myapplication.youtube.model.response.SearchVideo
import com.islam.hesn.myapplication.youtube.view.YoutubeRecyclerViewSearchPagingAdapter.YoutubeViewHolder

class YoutubeRecyclerViewSearchPagingAdapter(
    diffCallback: DiffUtil.ItemCallback<SearchVideo>,
    private val onVideoClick: ((videoId: String, videoTitle: String) -> Unit),
) :
    PagingDataAdapter<SearchVideo, YoutubeViewHolder>(diffCallback) {

    private var _binding: ItemLayoutYoutubeFirstChannelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {

        _binding = ItemLayoutYoutubeFirstChannelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return YoutubeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount == 0) R.layout.item_layout_youtube_first_channel
        else super.getItemViewType(position)
    }


    inner class YoutubeViewHolder(val binding: ItemLayoutYoutubeFirstChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: SearchVideo): Unit = with(itemView) {
            with(video.snippet) {

                binding.tvName.text = title
                val ivThumbnail = binding.ivThumbnail

                with(thumbnails) {
                    standard?.let {
                        Glide.with(itemView).load(standard.url).into(ivThumbnail)
                        return
                    }
                    high?.let {
                        Glide.with(itemView).load(high.url).into(ivThumbnail)
                        return
                    }
                    medium?.let {
                        Glide.with(itemView).load(medium.url).into(ivThumbnail)
                        return
                    }
                    default?.let {
                        Glide.with(itemView).load(default.url).into(ivThumbnail)
                        return
                    }
                }

                video.apply {

                    setOnClickListener { onVideoClick(video.id.videoId, title) }
                }

            }
        }

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _binding = null
    }
}
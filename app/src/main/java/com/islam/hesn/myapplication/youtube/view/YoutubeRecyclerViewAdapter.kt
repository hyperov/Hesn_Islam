package com.islam.hesn.myapplication.youtube.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.youtube.model.response.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout_youtube_first_channel.view.*


class YoutubeRecyclerViewAdapter(
    private val values: List<Video>,
//    private val onSurahItemClick: ((surahId: Int) -> Unit)? = null,
//    private val onAyaItemClick: ((surahId: Int, ayaId: Int) -> Unit)? = null
) : RecyclerView.Adapter<YoutubeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_youtube_first_channel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(video: Video) = with(itemView) {
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


//            setOnClickListener {
//                onAyaItemClick?.invoke(sura_id, aya_id)
//            }
            }
        }

    }
}
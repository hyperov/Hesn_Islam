package com.islam.hesn.myapplication.bible.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.AdapterStateBibleEnum
import com.islam.hesn.myapplication.bible.AdapterStateBibleEnum.*
import com.islam.hesn.myapplication.bible.model.response.Book
import com.islam.hesn.myapplication.bible.model.response.Chapter
import com.islam.hesn.myapplication.bible.model.response.Verse
import kotlinx.android.synthetic.main.item_layout_surah.view.*


class BibleMainRecyclerViewAdapter(
    private val books: List<Book>? = null,
    private val chapters: ArrayList<Chapter>? = null,
    private val verses: ArrayList<Verse>? = null,
    private val state: AdapterStateBibleEnum,
    private val onBookItemClick: ((bookNum: Int) -> Unit)? = null,
    private val onChapterItemClick: ((chapterNum: Int, ayaId: Int) -> Unit)? = null,
    private val onVerseItemClick: ((verseNum: Int, ayaId: Int) -> Unit)? = null
) : RecyclerView.Adapter<BibleMainRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_surah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = books!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = books!!.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(book: Book) = with(itemView) {
            with(book) {
                when (state) {
                    BOOKS -> {
                        item_num.text = bookNum.toString()
                        content.text = bookName
                        setOnClickListener {
                            onBookItemClick?.invoke(bookNum)
                        }
                    }
                    CHAPTERS -> {
//                        item_num.text = aya_id.toString()
//                        content.text = uthmani
//                        setOnClickListener {
//                            onChapterItemClick?.invoke(sura_id, aya_id)
//                    }
                    }
                    VERSES -> {
//                        item_num.text = aya_id.toString()
//                        content.text = uthmani
//                        setOnClickListener {
//                            onChapterItemClick?.invoke(sura_id, aya_id)
//            }
                    }
                }


            }


        }


    }
}
package com.islam.hesn.myapplication.bible.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum.*
import kotlinx.android.synthetic.main.item_layout_surah.view.*


class BibleMainRecyclerViewAdapter(
    private val books: List<Book>? = null,
    private val chapters: List<Chapter>? = null,
    private val verses: List<Verse>? = null,
    private val state: AdapterStateBibleEnum,
    private val onBookItemClick: ((book: Book) -> Unit)? = null,
    private val onChapterItemClick: ((chapterNum: Int) -> Unit)? = null,
    private val onVerseItemClick: ((verse: Verse) -> Unit)? = null
) : RecyclerView.Adapter<BibleMainRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_surah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (state) {
            BOOKS -> holder.bind(books!![position])
            CHAPTERS -> holder.bind(chapters!![position])
            VERSES -> holder.bind(verses!![position])
        }
    }

    override fun getItemCount(): Int = when (state) {
        BOOKS -> books!!.size
        CHAPTERS -> chapters!!.size
        VERSES -> verses!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(book: Book) = with(itemView) {
            with(book) {
                item_num.text = bookNum.toString()
                content.text = bookName
                setOnClickListener {
                    onBookItemClick?.invoke(this)
                }
            }
        }


        fun bind(chapter: Chapter) = with(itemView) {
            with(chapter) {
                item_num.text = chapterNum.toString()
                content.text = chapterNum.toString()
                setOnClickListener {
                    onChapterItemClick?.invoke(chapterNum)
                }
            }
        }

        fun bind(verse: Verse) = with(itemView) {
            with(verse) {
                item_num.text = verseNum.toString()
                content.text = verseContent.toString()
                setOnClickListener {
                    onVerseItemClick?.invoke(verse)
                }
            }
        }
    }
}
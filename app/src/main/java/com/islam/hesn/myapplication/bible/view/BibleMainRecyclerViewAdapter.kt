package com.islam.hesn.myapplication.bible.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum.*
import com.islam.hesn.myapplication.utils.convertDpToPixel
import kotlinx.android.synthetic.main.item_layout_surah.view.*


class BibleMainRecyclerViewAdapter(
    private val books: List<Book>? = null,
    private val chapters: List<Chapter>? = null,
    private val verses: List<Verse>? = null,
    private val state: AdapterStateBibleEnum,
    private val onBookItemClick: ((book: Book, title: String) -> Unit)? = null,
    private val onChapterItemClick: ((chapterNum: Int) -> Unit)? = null,
    private val onVerseItemClick: ((verse: Verse) -> Unit)? = null,
) : RecyclerView.Adapter<BibleMainRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_surah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (state) {
            BOOKS -> holder.bind(books!![position], position)
            CHAPTERS -> holder.bind(chapters!![position], position)
            VERSES -> holder.bind(verses!![position], position)
        }
    }

    override fun getItemCount(): Int = when (state) {
        BOOKS -> books!!.size
        CHAPTERS -> chapters!!.size
        VERSES -> verses!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(book: Book, position: Int) = with(itemView) {

            addPaddingToLastItem(position)

            with(book) {
                item_num.text = bookNum.toString()
                val arabicTitles = resources.getStringArray(R.array.bible_books)
                content.text = arabicTitles[position]
                setOnClickListener {
                    onBookItemClick?.invoke(this, arabicTitles[position])
                }
            }
        }

        private fun View.addPaddingToLastItem(position: Int) {
            if (position == itemCount - 1) {
                val layoutParams = cardText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.updateMargins(bottom = context.convertDpToPixel(16F)
                    .toInt())
                cardText.requestLayout()
            }
        }


        fun bind(chapter: Chapter, position: Int) = with(itemView) {

            addPaddingToLastItem(position)

            with(chapter) {
                item_num.text = chapterNum.toString()
                content.text = chapterNum.toString()
                setOnClickListener {
                    onChapterItemClick?.invoke(chapterNum)
                }
            }
        }

        fun bind(verse: Verse, position: Int) = with(itemView) {

            addPaddingToLastItem(position)

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
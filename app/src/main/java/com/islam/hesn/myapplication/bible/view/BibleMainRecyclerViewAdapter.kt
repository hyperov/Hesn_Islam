package com.islam.hesn.myapplication.bible.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.bible.model.response.bible.Book
import com.islam.hesn.myapplication.bible.model.response.bible.Chapter
import com.islam.hesn.myapplication.bible.model.response.bible.Verse
import com.islam.hesn.myapplication.bible.view.AdapterStateBibleEnum.*
import kotlinx.android.synthetic.main.item_layout_chapter.view.*
import kotlinx.android.synthetic.main.item_layout_surah.view.content
import kotlinx.android.synthetic.main.item_layout_surah.view.item_num


class BibleMainRecyclerViewAdapter(
    private val books: List<Book>? = null,
    private val chapters: List<Chapter>? = null,
    private val verses: List<Verse>? = null,
    private val state: AdapterStateBibleEnum,
    private val isVerse: Boolean,
    private val onBookItemClick: ((book: Book, title: String) -> Unit)? = null,
    private val onChapterItemClick: ((chapterNum: Int) -> Unit)? = null,
    private val onVerseItemClick: ((verse: Verse) -> Unit)? = null,
) : RecyclerView.Adapter<BibleMainRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_chapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (state) {
            BOOKS -> holder.bind(books!![position], position)
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

        fun bind(book: Book, position: Int) = with(itemView) {

            if (!isVerse)
                tvTranslateBible.isGone = true

            with(book) {
                item_num.text = bookNum.toString()
                val arabicTitles = resources.getStringArray(R.array.bible_books)
                content.text = arabicTitles[position]
                setOnClickListener {
                    onBookItemClick?.invoke(this, arabicTitles[position])
                }
            }
        }

        fun bind(chapter: Chapter) = with(itemView) {

            if (!isVerse)
                tvTranslateBible.isGone = true

            with(chapter) {
                item_num.text = chapterNum.toString()
                content.text = chapterNum.toString()
                setOnClickListener {
                    onChapterItemClick?.invoke(chapterNum)
                }
            }
        }

        fun bind(verse: Verse) = with(itemView) {

            if (isVerse)
                tvTranslateBible.isGone = false

            with(verse) {
                item_num.text = verseNum.toString()
                content.text = verseContent.trim()
                setOnClickListener {
                    onVerseItemClick?.invoke(verse)
                }
                setOnLongClickListener {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                    val clip = ClipData.newPlainText("العدد", verseContent.trim())
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "تم نسخ العدد بنجاح", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
            }
        }
    }
}
package com.islam.hesn.myapplication.quran.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH_LIST
import com.islam.hesn.myapplication.quran.model.response.arabic.AyaItem
import com.islam.hesn.myapplication.utils.BOOKMARK_AYA_NUMBER
import com.islam.hesn.myapplication.utils.BOOKMARK_SURAH_NUMBER
import com.islam.hesn.myapplication.utils.Prefs
import kotlinx.android.synthetic.main.item_layout_surah.view.*


class MySurahRecyclerViewAdapter(
    private val values: ArrayList<AyaItem>,
    private val state: AdapterStateQuranEnum,
    private val onSurahItemClick: ((surahId: Int) -> Unit)? = null,
    private val onAyaItemClick: ((surahId: Int, ayaId: Int) -> Unit)? = null,
    private val onLastReadClick: ((surahId: Int, ayaId: Int) -> Unit)? = null,
) : RecyclerView.Adapter<MySurahRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_surah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(ayaItem: AyaItem) = with(itemView) {

            with(ayaItem) {
                when (state) {
                    QURAN_SURAH_LIST -> {
                        ivLastRead.isVisible = false
                        item_num.text = sura_id.toString()
                        content.text = sura_name
                        setOnClickListener {
                            onSurahItemClick?.invoke(sura_id)
                        }
                    }
                    QURAN_SURAH -> {
                        ivLastRead.isVisible = true
                        ivLastRead.setOnClickListener {

                            notifyItemChanged(Prefs.getInt(BOOKMARK_AYA_NUMBER, 1) - 1)
                            onLastReadClick?.invoke(sura_id, aya_id)
                            ivLastRead.setImageDrawable(ResourcesCompat.getDrawable(resources,
                                R.drawable.ic_starred,
                                null))

                        }

                        if (Prefs.getInt(BOOKMARK_AYA_NUMBER, 1) == aya_id && Prefs.getInt(
                                BOOKMARK_SURAH_NUMBER, 1) == sura_id
                        )
                            ivLastRead.setImageDrawable(ResourcesCompat.getDrawable(resources,
                                R.drawable.ic_starred,
                                null))
                        else
                            ivLastRead.setImageDrawable(ResourcesCompat.getDrawable(resources,
                                R.drawable.ic_unstarred,
                                null))

                        item_num.text = aya_id.toString()
                        content.text = standard_full
                        setOnClickListener {
                            onAyaItemClick?.invoke(sura_id, aya_id)
                        }
                    }
                }


            }


        }


    }
}
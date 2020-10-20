package com.islam.hesn.myapplication.quran.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islam.hesn.myapplication.R
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH
import com.islam.hesn.myapplication.quran.model.response.arabic.AdapterStateQuranEnum.QURAN_SURAH_LIST
import com.islam.hesn.myapplication.quran.model.response.arabic.SurahItem
import kotlinx.android.synthetic.main.item_layout_surah.view.*


class MySurahRecyclerViewAdapter(
    private val values: ArrayList<SurahItem>,
    private val state: AdapterStateQuranEnum,
    private val onSurahItemClick: ((surahId: Int) -> Unit)? = null,
    private val onAyaItemClick: ((surahId: Int, ayaId: Int) -> Unit)? = null,
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

        fun bind(surahItem: SurahItem) = with(itemView) {

            with(surahItem) {
                when (state) {
                    QURAN_SURAH_LIST -> {
                        item_num.text = sura_id.toString()
                        content.text = sura_name
                        setOnClickListener {
                            onSurahItemClick?.invoke(sura_id)
                        }
                    }
                    QURAN_SURAH -> {
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
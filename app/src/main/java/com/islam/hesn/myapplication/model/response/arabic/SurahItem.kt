package com.islam.hesn.myapplication.model.response.arabic

data class SurahItem(
    val aya_id: Int,
    val aya_id_display: String,
    val gid: Int,
    val juz_id: Int,
    val page_id: Int,
    val standard: String,
    val standard_full: String,
    val sura_id: Int,
    val sura_name: String,
    val sura_name_en: String,
    val sura_name_romanization: String,
    val uthmani: String
)
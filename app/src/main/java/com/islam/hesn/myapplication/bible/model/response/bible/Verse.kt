package com.islam.hesn.myapplication.bible.model.response.bible

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Verse(
    @field:SerializedName("chapter")
    val chapterNum: Int,
    @field:SerializedName("verse")
    val verseNum: Int,
    @field:SerializedName("text")
    val verseContent: String,
    @field:SerializedName("book_name")
    val bookName: String?,
    @field:SerializedName("book_nr")
    val bookNum: Int?,
) : Parcelable
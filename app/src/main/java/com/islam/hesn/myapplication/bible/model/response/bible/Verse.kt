package com.islam.hesn.myapplication.bible.model.response.bible

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Verse(
    @field:SerializedName("verse_nr")
    val verseNum: Int,
    @field:SerializedName("verse")
    val verseContent: String
) : Parcelable
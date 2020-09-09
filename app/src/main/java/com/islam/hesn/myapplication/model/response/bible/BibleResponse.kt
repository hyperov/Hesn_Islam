package com.islam.hesn.myapplication.model.response.bible

import com.google.gson.annotations.SerializedName

data class BibleResponse(
    val direction: String,
    val type: String,
    val version: Version,
    @field:SerializedName("version_ref")
    val translationName: String
)
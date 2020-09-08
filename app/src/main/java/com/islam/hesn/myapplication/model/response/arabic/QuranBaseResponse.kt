package com.islam.hesn.myapplication.model.response.arabic

import com.google.gson.annotations.SerializedName

data class QuranBaseResponse(
    @SerializedName("list")
    var list: ArrayList<SurahItem>
)
package com.islam.hesn.myapplication.model.response.translation

import com.google.gson.annotations.SerializedName

data class AyaTranslationItem(
   @field:SerializedName("result") val aya: Aya
)
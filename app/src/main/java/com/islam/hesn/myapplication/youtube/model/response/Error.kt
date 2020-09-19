package com.islam.hesn.myapplication.youtube.model.response

data class Error(
    val domain: String,
    val location: String,
    val locationType: String,
    val message: String,
    val reason: String
)
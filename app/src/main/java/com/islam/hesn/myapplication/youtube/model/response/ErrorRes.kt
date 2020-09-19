package com.islam.hesn.myapplication.youtube.model.response

data class ErrorRes(
    val code: Int,
    val errors: List<Error>,
    val message: String
)
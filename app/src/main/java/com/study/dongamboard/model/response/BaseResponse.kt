package com.study.dongamboard.model.response

data class BaseResponse<T> (
    val code: Int,
    val status: String,
    val result: T,
)
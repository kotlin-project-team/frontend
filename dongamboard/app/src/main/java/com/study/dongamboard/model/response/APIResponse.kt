package com.study.dongamboard.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class APIResponse<T> (
    @field:Json(name = "code")
    val code: Int,

    @field:Json(name = "result")
    val result: List<T>,

    @field:Json(name = "status")
    val status: String,
) : Serializable
package com.study.dongamboard.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class NoticeRequest(
    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "content")
    val content: String,
) : Serializable

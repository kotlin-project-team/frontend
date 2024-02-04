package com.study.dongamboard.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.study.dongamboard.type.BoardCategory
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PostRequest (
    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "content")
    val content: String,

    @field:Json(name = "category")
    val category: BoardCategory,
) : Serializable
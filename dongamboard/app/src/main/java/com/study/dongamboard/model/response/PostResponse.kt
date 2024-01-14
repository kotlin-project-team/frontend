package com.study.dongamboard.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class PostResponse(
    @field:Json(name = "id")
    val id: Long,

    @field:Json(name = "user")
    val user: UserInformation,

    @field:Json(name = "title")
    val title: String,

    @field:Json(name = "content")
    val content: String,

    @field:Json(name = "likes")
    val likes: Int,

    @field:Json(name = "views")
    val views: Int,
) : Serializable
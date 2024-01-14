package com.study.dongamboard.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CommentResponse(
    @field:Json(name = "id")
    val id: Long,

    @field:Json(name = "userId")
    val userId: Long,

    @field:Json(name = "postId")
    val postId: Long,

    @field:Json(name = "content")
    val content: String,
) : Serializable


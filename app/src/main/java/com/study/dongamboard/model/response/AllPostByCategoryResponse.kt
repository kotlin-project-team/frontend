package com.study.dongamboard.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class AllPostByCategoryResponse(
    @field:Json(name = "posts")
    val posts: List<PostResponse>,

    @field:Json(name = "postCount")
    val postCount: Int,
) : Serializable
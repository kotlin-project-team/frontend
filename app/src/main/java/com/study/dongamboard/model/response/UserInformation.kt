package com.study.dongamboard.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UserInformation(
    @field:Json(name = "id")
    val id: Long,

    @field:Json(name = "studentId")
    val studentId: String,

    @field:Json(name = "nickname")
    val nickname: String,
) : Serializable

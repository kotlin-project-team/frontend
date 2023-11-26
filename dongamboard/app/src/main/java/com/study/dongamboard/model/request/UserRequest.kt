package com.study.dongamboard.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UserRequest (
    @field:Json(name = "studentId")
    val studentId: String,

    @field:Json(name = "password")
    val password: String,

    @field:Json(name = "nickname")
    val nickname: String,

    @field:Json(name = "deviceToken")
    val deviceToken: String
) : Serializable
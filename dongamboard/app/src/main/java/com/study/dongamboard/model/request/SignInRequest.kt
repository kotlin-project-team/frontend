package com.study.dongamboard.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SignInRequest(
    @field:Json(name = "studentId")
    val studentId: String,

    @field:Json(name = "password")
    val password: String
) : Serializable
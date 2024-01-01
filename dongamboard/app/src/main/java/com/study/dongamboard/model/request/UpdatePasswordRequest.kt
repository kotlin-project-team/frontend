package com.study.dongamboard.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UpdatePasswordRequest(
    @field:Json(name = "oldPassword")
    val oldPassword: String,

    @field:Json(name = "newPassword")
    val newPassword: String,
) : Serializable
package com.study.dongamboard.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UpdateNicknameRequest(
    @field:Json(name = "nickname")
    val nickname: String,
) : Serializable
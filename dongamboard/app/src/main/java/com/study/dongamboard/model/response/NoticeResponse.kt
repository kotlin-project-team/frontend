package com.study.dongamboard.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "notice_table")
@JsonClass(generateAdapter = true)
data class NoticeResponse(
@field:Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // TODO: 추후 활성화
//    @field:Json(name = "title")
//    @ColumnInfo
//    val title: String,

    @field:Json(name = "content")
    @ColumnInfo
    val content: String? = null,
) : Serializable
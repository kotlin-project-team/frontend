package com.study.dongamboard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(tableName = "comment_table")
@JsonClass(generateAdapter = true)
data class CommentData(
    @field:Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @field:Json(name = "post_id")
    @ColumnInfo(name = "post_id")
    var postId: Int,

    @field:Json(name = "content")
    @ColumnInfo
    var content: String,

    @field:Json(name = "nickname")
    @ColumnInfo
    var nickname: String
) : Serializable

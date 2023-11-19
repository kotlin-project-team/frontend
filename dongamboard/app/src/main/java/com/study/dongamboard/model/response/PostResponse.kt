package com.study.dongamboard.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.study.dongamboard.model.CommentData
import com.study.dongamboard.type.BoardCategoryType
import java.io.Serializable

@Entity(tableName = "post_table")
@JsonClass(generateAdapter = true)
data class PostResponse(
    @field:Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:Json(name = "userId")
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @field:Json(name = "title")
    @ColumnInfo
    val title: String,

    @field:Json(name = "content")
    @ColumnInfo
    val content: String? = null,

    @field:Json(name = "category")
    @ColumnInfo
    val category: BoardCategoryType? = null,

    @field:Json(name = "likes")
    @ColumnInfo
    val likes: Int? = null,

    @field:Json(name = "views")
    @ColumnInfo
    val views: Int? = null,

    @field:Json(name = "comments")
    @ColumnInfo
    val comments: List<CommentData>? = null
) : Serializable
package com.study.dongamboard.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.study.dongamboard.Converters
import java.io.Serializable
import java.sql.Timestamp

@Entity(tableName = "post_table")
data class PostData(
    @PrimaryKey
    var id: Int,
    var userId: Int,
    var title: String,
    var content: String,
    var category: String,
    var likes: Int,
    var views: Int
//    var createdAt: Timestamp,
//    var updatedAt: Timestamp
) : Serializable

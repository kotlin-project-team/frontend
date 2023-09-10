package com.study.dongamboard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.study.dongamboard.Converters
import java.io.Serializable
import java.sql.Timestamp

@Entity(tableName = "post_table")
data class PostData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var content: String,
    @ColumnInfo
    var category: String,
    @ColumnInfo
    var likes: Int,
    @ColumnInfo
    var views: Int
//    var createdAt: Timestamp,
//    var updatedAt: Timestamp
) : Serializable

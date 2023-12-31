package com.study.dongamboard.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notice_table")
data class NoticeData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var content: String? = null,
    @ColumnInfo
    var category: String? = null
) : Serializable

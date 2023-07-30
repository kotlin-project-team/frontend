package com.study.dongamboard.model

import java.io.Serializable
import java.sql.Timestamp

data class NoticeData(var id: Int,
                      var title: String,
                      var content: String,
                      var category: String,
                      var createdAt: Timestamp,
                      var updatedAt: Timestamp
) : Serializable

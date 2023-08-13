package com.study.dongamboard.model

import java.io.Serializable
import java.sql.Timestamp

data class PostData(var id: Int,
                    var userId: Int,
                    var title: String,
                    var content: String,
                    var category: String,
                    var likes: Int,
                    var views: Int,
                    var createdAt: Timestamp,
                    var updatedAt: Timestamp
) : Serializable

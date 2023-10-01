package com.study.dongamboard.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.study.dongamboard.model.CommentData

@Dao
interface CommentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: CommentData)

    @Update
    fun updateComment(comment: CommentData)

    @Delete
    fun deleteComment(comment: CommentData)

    @Query("SELECT * FROM comment_table")
    fun findAll(): List<CommentData>

    @Query("SELECT * FROM comment_table WHERE post_id = :postId")
    fun findCommentsByPostId(postId: Int): List<CommentData>

    @Query("SELECT * FROM comment_table WHERE id = :id")
    fun findCommentById(id: Int): CommentData

}
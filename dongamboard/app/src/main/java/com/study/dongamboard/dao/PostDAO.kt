package com.study.dongamboard.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.study.dongamboard.model.PostResponse

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: PostResponse)

    @Update
    fun updatePost(post: PostResponse)

    @Delete
    fun deletePost(post: PostResponse)

    @Query("SELECT * FROM post_table")
    fun findAll(): List<PostResponse>

    @Query("SELECT * FROM post_table WHERE id = :id")
    fun findPostById(id: Int): PostResponse

}
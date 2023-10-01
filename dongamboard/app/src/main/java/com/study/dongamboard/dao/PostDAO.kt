package com.study.dongamboard.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.study.dongamboard.model.PostData

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: PostData)

    @Update
    fun updatePost(post: PostData)

    @Delete
    fun deletePost(post: PostData)

    @Query("SELECT * FROM post_table")
    fun findAll(): List<PostData>

    @Query("SELECT * FROM post_table WHERE id = :id")
    fun findPostById(id: Int): PostData

}
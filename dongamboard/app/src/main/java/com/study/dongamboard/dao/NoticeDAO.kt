package com.study.dongamboard.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.study.dongamboard.model.NoticeData

@Dao
interface NoticeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotice(notice: NoticeData)

    @Update
    fun updateNotice(notice: NoticeData)

    @Delete
    fun deleteNotice(notice: NoticeData)

    @Query("SELECT * FROM notice_table")
    fun findAll(): List<NoticeData>

    @Query("SELECT * FROM notice_table WHERE id = :id")
    fun findNoticeById(id: Int): NoticeData

}
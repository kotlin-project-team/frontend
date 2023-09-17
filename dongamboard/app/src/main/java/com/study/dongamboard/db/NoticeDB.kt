package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.study.dongamboard.dao.NoticeDAO
import com.study.dongamboard.model.NoticeData

@Database(entities = [NoticeData::class], version = 1, exportSchema = false)
abstract class NoticeDB: RoomDatabase() {

    abstract fun noticeDao(): NoticeDAO

    companion object {
        @Volatile
        private var instance: NoticeDB? = null

        @Synchronized
        fun getInstance(context: Context): NoticeDB? {
            if (instance == null)
                synchronized(NoticeDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoticeDB::class.java,
                        "notice.db"
                    ).allowMainThreadQueries().build()  // 추후 allowMainThreadQueries() 제거
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }

}
package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.study.dongamboard.converter.NoticeListTypeConverter
import com.study.dongamboard.dao.NoticeDAO
import com.study.dongamboard.model.NoticeData

@Database(entities = [NoticeData::class], version = 1, exportSchema = false)
@TypeConverters(NoticeListTypeConverter::class)
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
                    )
                        .allowMainThreadQueries()  // 추후 allowMainThreadQueries() 제거
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }

}
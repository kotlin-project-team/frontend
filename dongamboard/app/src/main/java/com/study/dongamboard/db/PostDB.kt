package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.study.dongamboard.Converters
import com.study.dongamboard.dao.PostDAO
import com.study.dongamboard.model.PostData

@Database(entities = [PostData::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class PostDB : RoomDatabase() {
    abstract fun postDao(): PostDAO

    companion object {
        @Volatile
        private var instance: PostDB? = null

        @Synchronized
        fun getInstance(context: Context): PostDB? {
            if (instance == null)
                synchronized(PostDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDB::class.java,
                        "post.db"
                    ).build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }

}
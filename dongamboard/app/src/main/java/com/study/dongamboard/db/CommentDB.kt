package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.study.dongamboard.dao.CommentDAO
import com.study.dongamboard.model.CommentData

@Database(entities = [CommentData::class], version = 1, exportSchema = false)
abstract class CommentDB : RoomDatabase() {

    abstract fun commentDao(): CommentDAO

    companion object {
        @Volatile
        private var instance: CommentDB? = null

        @Synchronized
        fun getInstance(context: Context): CommentDB? {
            if (instance == null)
                synchronized(CommentDB::class) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            CommentDB::class.java,
                            "comment.db"
                        )
                        .allowMainThreadQueries()
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}
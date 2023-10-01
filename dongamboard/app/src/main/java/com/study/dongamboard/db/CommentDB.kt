package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.study.dongamboard.dao.CommentDAO
import com.study.dongamboard.model.CommentData

@Database(entities = [CommentData::class], version = 1, exportSchema = false)
abstract class CommentDB : RoomDatabase() {

    abstract fun commentDao(): CommentDAO

    companion object {
        @Volatile
        private var instance: CommentDB? = null

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

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
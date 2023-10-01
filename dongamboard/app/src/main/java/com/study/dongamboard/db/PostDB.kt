package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.study.dongamboard.CommentListTypeConverter
import com.study.dongamboard.dao.PostDAO
import com.study.dongamboard.model.PostData

@Database(entities = [PostData::class], version = 1, exportSchema = false)
@TypeConverters(CommentListTypeConverter::class)
abstract class PostDB : RoomDatabase() {
    abstract fun postDao(): PostDAO

    companion object {
        @Volatile
        private var instance: PostDB? = null

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        @Synchronized
        fun getInstance(context: Context): PostDB? {
            if (instance == null)
                synchronized(PostDB::class) {
                    instance = Room
                        .databaseBuilder(
                        context.applicationContext,
                        PostDB::class.java,
                        "post.db"
                    )
                        .addTypeConverter(CommentListTypeConverter(moshi))
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
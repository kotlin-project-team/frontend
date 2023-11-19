package com.study.dongamboard.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.converter.CommentListTypeConverter
import com.study.dongamboard.converter.PostListTypeConverter
import com.study.dongamboard.dao.PostDAO
import com.study.dongamboard.model.response.PostResponse

@Database(entities = [PostResponse::class], version = 1, exportSchema = false)
@TypeConverters(
    CommentListTypeConverter::class,
    PostListTypeConverter::class
)
abstract class PostDB : RoomDatabase() {
    abstract fun postDao(): PostDAO

    companion object {
        @Volatile
        private var instance: PostDB? = null

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
                        .addTypeConverter(CommentListTypeConverter(APIObject.moshi))
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }

}
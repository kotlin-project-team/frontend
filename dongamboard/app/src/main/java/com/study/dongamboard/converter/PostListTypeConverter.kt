package com.study.dongamboard.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.study.dongamboard.model.PostData

@ProvidedTypeConverter
class PostListTypeConverter(private val moshi: Moshi) {

    @TypeConverter
    fun fromString(value: String): List<PostData>? {
        val listType = Types.newParameterizedType(List::class.java, PostData::class.java)
        val adapter: JsonAdapter<List<PostData>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromImage(type: List<PostData>): String {
        val listType = Types.newParameterizedType(List::class.java, PostData::class.java)
        val adapter: JsonAdapter<List<PostData>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

}
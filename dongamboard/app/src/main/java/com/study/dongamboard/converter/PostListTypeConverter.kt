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
    fun fromString(value: String): List<PostResponse>? {
        val listType = Types.newParameterizedType(List::class.java, PostResponse::class.java)
        val adapter: JsonAdapter<List<PostResponse>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromImage(type: List<PostResponse>): String {
        val listType = Types.newParameterizedType(List::class.java, PostResponse::class.java)
        val adapter: JsonAdapter<List<PostResponse>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

}
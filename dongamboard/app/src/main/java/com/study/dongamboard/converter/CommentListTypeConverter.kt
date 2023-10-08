package com.study.dongamboard.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.study.dongamboard.model.CommentData

@ProvidedTypeConverter
class CommentListTypeConverter(private val moshi: Moshi) {

    @TypeConverter
    fun fromString(value: String): List<CommentData>? {
        val listType = Types.newParameterizedType(List::class.java, CommentData::class.java)
        val adapter: JsonAdapter<List<CommentData>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromImage(type: List<CommentData>): String {
        val listType = Types.newParameterizedType(List::class.java, CommentData::class.java)
        val adapter: JsonAdapter<List<CommentData>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

}
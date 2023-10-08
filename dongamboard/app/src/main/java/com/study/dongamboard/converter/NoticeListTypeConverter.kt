package com.study.dongamboard.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.study.dongamboard.model.NoticeData

@ProvidedTypeConverter
class NoticeListTypeConverter(private val moshi: Moshi) {

    @TypeConverter
    fun fromString(value: String): List<NoticeData>? {
        val listType = Types.newParameterizedType(List::class.java, NoticeData::class.java)
        val adapter: JsonAdapter<List<NoticeData>> = moshi.adapter(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromImage(type: List<NoticeData>): String {
        val listType = Types.newParameterizedType(List::class.java, NoticeData::class.java)
        val adapter: JsonAdapter<List<NoticeData>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

}
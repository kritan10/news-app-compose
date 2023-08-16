package com.example.newsapp.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class NewsConverter {
    private val gson = Gson()
    private val type = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun fromCategoryList(category: List<String>): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun toCategoryList(string: String): List<String> {
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}
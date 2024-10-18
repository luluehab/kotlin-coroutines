package com.example.downloadworker.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): ArrayList<String>? {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return value?.let { gson.fromJson(it, listType) }
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String? {
        return gson.toJson(list)
    }
}
package com.example.rickmortyalbum.data

import androidx.room.TypeConverter
import com.google.gson.Gson

object Converters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return Gson().fromJson(value, List::class.java as Class<List<String>>)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
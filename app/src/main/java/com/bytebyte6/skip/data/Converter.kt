package com.bytebyte6.skip.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun listToJson(sport: List<RealSport>): String {
        return gson.toJson(sport)
    }

    @TypeConverter
    fun jsonToList(json: String): List<RealSport> {
        return gson.fromJson(json, object : TypeToken<List<RealSport>>(){}.type)
    }
}
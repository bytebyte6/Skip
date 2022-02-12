package com.bytebyte6.skip.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun sportToJson(sport: Sport): String {
        return gson.toJson(sport)
    }

    @TypeConverter
    fun jsonToSport(json: String): Sport {
        return gson.fromJson(json, Sport::class.java)
    }

    @TypeConverter
    fun listToJson(sport: List<Sport>): String {
        return gson.toJson(sport)
    }

    @TypeConverter
    fun jsonToList(json: String): List<Sport> {
        return gson.fromJson(json, object : TypeToken<List<Sport>>(){}.type)
    }
}
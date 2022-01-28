package com.bytebyte6.skip.data

import androidx.room.TypeConverter
import com.google.gson.Gson

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
}
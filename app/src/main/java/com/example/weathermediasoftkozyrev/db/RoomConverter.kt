package com.example.weathermediasoftkozyrev.db

import androidx.room.TypeConverter
import com.example.weathermediasoftkozyrev.model.*
import com.google.gson.Gson


class RoomConverter {

    @TypeConverter
    fun listToWeatherEntity(value: List<Weather>?):String = Gson().toJson(value)

    @TypeConverter
    fun weatherEntityToList(value: String) =
        Gson().fromJson(value, Array<Weather>::class.java).toList()


    @TypeConverter
    fun listToDaily(value: List<Daily>?): String = Gson().toJson(value)

    @TypeConverter
    fun dailyToList(value: String) =
        Gson().fromJson(value, Array<Daily>::class.java).toList()









}





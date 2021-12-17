package com.example.weathermediasoftkozyrev.db

import androidx.room.TypeConverter
import com.example.weathermediasoftkozyrev.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class RoomConverter {


    @TypeConverter
    fun listToCurrentDay(value: List<Current>?): String = Gson().toJson(value)

    @TypeConverter
    fun currentDayToList(value: String) =
        Gson().fromJson(value, Array<Current>::class.java).toList()


    @TypeConverter
    fun listToWeatherEntity(value: List<Weather>?):String = Gson().toJson(value)

    @TypeConverter
    fun weatherEntityToList(value: String) =
        Gson().fromJson(value, Array<Weather>::class.java).toList()


    @TypeConverter
    fun fromTempEntity(tempEntity: Temp): String {
        return Gson().toJson(tempEntity)
    }

    @TypeConverter
    fun toTempEntity(tempEntity: String): Temp {
        return Gson().fromJson(tempEntity, Temp::class.java)
    }

    @TypeConverter
    fun fromCoordEntity(coordEntity: CoordEntity): String {
        return Gson().toJson(coordEntity)
    }

    @TypeConverter
    fun toCoordEntity(coordEntity: String): CoordEntity {
        return Gson().fromJson(coordEntity, CoordEntity::class.java)
    }


    @TypeConverter
    fun fromCurrentEntity(current: Current): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun toCurrentEntity(current: String): Current {
        return Gson().fromJson(current, Current::class.java)
    }


    @TypeConverter
    fun fromDailyEntity(dailly: Daily): String {
        return Gson().toJson(dailly)
    }

    @TypeConverter
    fun toDailyEntity(dailly: String): Daily {
        return Gson().fromJson(dailly, Daily::class.java)
    }

    @TypeConverter
    fun listToDaily(value: List<Daily>?): String = Gson().toJson(value)

    @TypeConverter
    fun dailyToList(value: String) =
        Gson().fromJson(value, Array<Daily>::class.java).toList()


    @TypeConverter
    fun fromDCityEntity(city: CityEntity): String {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun toDCityEntity(city: String): CityEntity {
        return Gson().fromJson(city, CityEntity::class.java)
    }

    @TypeConverter
    fun fromFeelsLikeEntity(feelsLike: FeelsLike): String {
        return Gson().toJson(feelsLike)
    }

    @TypeConverter
    fun toFeelsLikeEntity(feelsLike: String): FeelsLike {
        return Gson().fromJson(feelsLike, FeelsLike::class.java)
    }




}





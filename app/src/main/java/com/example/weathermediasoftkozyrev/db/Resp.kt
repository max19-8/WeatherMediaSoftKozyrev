package com.example.weathermediasoftkozyrev.db
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

data class RespEntity(
    var id: Int?,
    var lon: Float?,
    var lat: Float?,
    var daily:List<CurrentDayEntity>
)
data class CurrentDayEntity(
    var dt: Int,
    var weather: List<WeatherEntity>,
    var temp: TempEntity,
    var sunrise: Int ,
    var sunset: Int ,
    var pressure: Int ,
    var wind_speed: Double ,
    var clouds: Int,
    var dew_point: Double
)

data class TempEntity(
    var day: Double,
    var eve: Double ,
    var max: Double,
    var min: Double,
    var morn: Double ,
    var night: Double
)

data class WeatherEntity(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
)
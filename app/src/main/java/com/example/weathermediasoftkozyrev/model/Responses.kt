package com.example.weathermediasoftkozyrev.model

import androidx.room.*
import com.example.weathermediasoftkozyrev.db.CityEntity

@Entity(tableName = "resp_weather")
data class Responses(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val current: Current,
    val daily: List<Daily>,
    val lat: Float?,
    val lon: Float?,
    val timezone: String,
    val timezone_offset: Int,
    @Embedded(prefix = "city_")
    val city:CityEntity? = null)




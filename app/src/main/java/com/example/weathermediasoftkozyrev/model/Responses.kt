package com.example.weathermediasoftkozyrev.model

import androidx.room.*

@Entity(tableName = "resp_weather")
data class Responses(
    @PrimaryKey(autoGenerate = true)
    var id: Int =-1,
    @Embedded
    val current: Current,
    val daily: List<Daily>,
    val lat: Float?,
    val lon: Float?,
    val timezone: String,
    val timezone_offset: Int)




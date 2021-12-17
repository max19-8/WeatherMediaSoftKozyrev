package com.example.weathermediasoftkozyrev.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities_db")
data class CityEntity (
    @PrimaryKey(autoGenerate = false)
    val _id: Long ,
    val coord: CoordEntity,
    val country: String,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)
data class CoordEntity(
    val lat: Double,
    val lon: Double
)
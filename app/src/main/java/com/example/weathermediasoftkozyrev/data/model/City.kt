package com.example.weathermediasoftkozyrev.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities_db")
data class City (
    @PrimaryKey(autoGenerate = false)
    val _id: Int,
    @Embedded
    val coord: CoordEntity,
    val country: String,
    val name: String,
)
 class CoordEntity(
    val lat: Double,
    val lon: Double
)
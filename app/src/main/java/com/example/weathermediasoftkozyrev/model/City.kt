package com.example.weathermediasoftkozyrev.model

data class City(
    val coord: Coord,
    val country: String,
    val _id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)
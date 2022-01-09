package com.example.weathermediasoftkozyrev.data.repository.detailrepository

import com.example.weathermediasoftkozyrev.data.model.WeatherResponses

interface CityWeatherRepository {
    suspend fun getWeatherRemote(lat: Float, lon: Float): WeatherResponses
    suspend fun saveWeather(respEntity: WeatherResponses?)
    fun getWeatherDatabase(id: Int): WeatherResponses
    fun checkWeatherInDatabase(id: Int): Boolean
}
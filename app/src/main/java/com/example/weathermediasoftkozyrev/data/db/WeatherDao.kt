package com.example.weathermediasoftkozyrev.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weathermediasoftkozyrev.data.model.City
import com.example.weathermediasoftkozyrev.data.model.WeatherResponses

@Dao
interface WeatherDao {
    @Query("SELECT * FROM cities_db")
    fun getCities(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCities(cities: List<City>)

    @Query("SELECT * FROM resp_weather WHERE id=:id")
    fun getWeather(id: Int): WeatherResponses

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherResponses?)

    @Query("SELECT EXISTS (SELECT 1 FROM resp_weather WHERE id = :id)")
    fun checkWeatherInDatabase(id: Int): Boolean
}
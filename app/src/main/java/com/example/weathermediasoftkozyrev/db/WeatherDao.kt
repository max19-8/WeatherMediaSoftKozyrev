package com.example.weathermediasoftkozyrev.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weathermediasoftkozyrev.model.City
import com.example.weathermediasoftkozyrev.model.Responses

@Dao
interface WeatherDao {
    //cities
    @Query("SELECT * FROM cities_db")
    fun getCities(): LiveData<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCities(cities: List<City>)

//weather

   @Query("SELECT * FROM resp_weather WHERE id=:id")
    fun getWeather(id:Int): Responses


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend  fun insertWeather(weather: Responses?)

    @Query("SELECT EXISTS (SELECT 1 FROM resp_weather WHERE id = :id)")
    fun exists(id: Int): Boolean




}

package com.example.weathermediasoftkozyrev.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weathermediasoftkozyrev.model.Responses

@Dao
interface WeatherDao {
    //cities
    @Query("SELECT * FROM cities_db")
    fun getCities(): LiveData<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCities(cities: List<CityEntity>)

//weather


//    @Query("SELECT * FROM resp_weather WHERE cityId=:cityId")
//    suspend fun getWeather2(cityId:Long): Responses

//    @Query("SELECT * FROM resp_weather WHERE lat=:lat AND lon=:lon")
//     fun getWeather2(lat:Float,lon: Float): Responses

//    @Query("SELECT * FROM resp_weather  INNER JOIN cities_db ON lat =:lat UNION ALL  lon =:lon")
//    fun getWeather(lat: Float, lon: Float): Responses

    @Query("SELECT * FROM resp_weather  WHERE c ")
    fun getWeather(lat:Float,lon:Float): Responses


    @Query("SELECT * FROM Subscription WHERE lableId IN (:lableID)")
    fun getLabledSubscriptions(lableID : Int): Flow<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Responses?)



}

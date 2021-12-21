package com.example.weathermediasoftkozyrev.repository

import com.example.weathermediasoftkozyrev.api.RetrofitInstance
import com.example.weathermediasoftkozyrev.db.WeatherDao
import com.example.weathermediasoftkozyrev.model.Responses
import com.example.weathermediasoftkozyrev.utils.Constants


class CityWeatherRepository(private val weatherDao: WeatherDao){

suspend fun getDetail(lat: Float,lon:Float) : Responses =
    RetrofitInstance.apiService.getDetail(
        lat = lat, lon = lon,
        exclude = Constants.exclude,
        units = Constants.units,
        lang = Constants.language,
        appId = Constants.API_KEY )

   suspend fun saveWeather(respEntity: Responses?)= weatherDao.insertWeather(respEntity)

     fun getWeather(id:Int): Responses = weatherDao.getWeather(id)

    fun exist(id:Int) = weatherDao.exists(id)



}








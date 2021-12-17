package com.example.weathermediasoftkozyrev.repository


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weathermediasoftkozyrev.api.RetrofitInstance
import com.example.weathermediasoftkozyrev.db.RespEntity
import com.example.weathermediasoftkozyrev.db.WeatherDao
import com.example.weathermediasoftkozyrev.db.WeatherEntity
import com.example.weathermediasoftkozyrev.model.Responses
import com.example.weathermediasoftkozyrev.utils.Constants


class CityWeatherRepository(private val weatherDao: WeatherDao){
//     suspend fun getCityWeatherDescription(
//        cityId: Int
//    ): Response<CityWeatherResponse> = RetrofitInstance.apiService.getCityWeatherDescription(id = cityId, cnt = Constants.cnt, units = Constants.units, appId = Constants.API_KEY,lang = Constants.language)

suspend fun getDetail(lat: Float,lon:Float) : Responses =
    RetrofitInstance.apiService.getDetail( lat = lat, lon = lon, exclude = Constants.exclude,units = Constants.units,lang = Constants.language,appId = Constants.API_KEY )

 suspend fun saveWeather(respEntity: Responses?)= weatherDao.insertWeather(respEntity)

    suspend fun getWeather(latnityde:Float,londityde:Float) : Responses = weatherDao.getWeather(latnityde, londityde)

}








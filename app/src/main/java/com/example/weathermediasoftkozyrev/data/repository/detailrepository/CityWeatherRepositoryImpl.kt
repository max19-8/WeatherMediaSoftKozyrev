package com.example.weathermediasoftkozyrev.data.repository.detailrepository

import com.example.weathermediasoftkozyrev.data.api.RetrofitInstance
import com.example.weathermediasoftkozyrev.data.model.WeatherResponses
import com.example.weathermediasoftkozyrev.data.db.WeatherDao
import com.example.weathermediasoftkozyrev.domain.utils.Constants

class CityWeatherRepositoryImpl(private val weatherDao: WeatherDao) : CityWeatherRepository {

    override suspend fun getWeatherRemote(lat: Float, lon: Float): WeatherResponses =
        RetrofitInstance.apiService.getDetail(
            lat = lat, lon = lon,
            exclude = Constants.exclude,
            units = Constants.units,
            lang = Constants.language,
            appId = Constants.API_KEY
        )

    override suspend fun saveWeather(respEntity: WeatherResponses?) =
        weatherDao.insertWeather(respEntity)

    override fun getWeatherDatabase(id: Int): WeatherResponses = weatherDao.getWeather(id)

    override fun checkWeatherInDatabase(id: Int): Boolean = weatherDao.checkWeatherInDatabase(id)
}
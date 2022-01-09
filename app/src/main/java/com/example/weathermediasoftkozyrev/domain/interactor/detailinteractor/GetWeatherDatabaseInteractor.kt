package com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor

import com.example.weathermediasoftkozyrev.data.model.WeatherResponses
import com.example.weathermediasoftkozyrev.data.repository.detailrepository.CityWeatherRepositoryImpl

class GetWeatherDatabaseInteractor(private val cityWeatherRepositoryImpl: CityWeatherRepositoryImpl) {
    fun getWeatherDatabase(id: Int): WeatherResponses =
        cityWeatherRepositoryImpl.getWeatherDatabase(id)
}
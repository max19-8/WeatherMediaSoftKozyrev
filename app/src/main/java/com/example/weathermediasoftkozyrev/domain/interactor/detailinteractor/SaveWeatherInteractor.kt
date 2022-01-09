package com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor

import com.example.weathermediasoftkozyrev.data.model.WeatherResponses
import com.example.weathermediasoftkozyrev.data.repository.detailrepository.CityWeatherRepositoryImpl

class SaveWeatherInteractor(private val cityWeatherRepositoryImpl: CityWeatherRepositoryImpl) {
    suspend fun saveWeather(respEntity: WeatherResponses?) =
        cityWeatherRepositoryImpl.saveWeather(respEntity)
}
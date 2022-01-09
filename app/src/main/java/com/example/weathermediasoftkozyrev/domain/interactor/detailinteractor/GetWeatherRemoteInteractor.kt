package com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor

import com.example.weathermediasoftkozyrev.data.model.WeatherResponses
import com.example.weathermediasoftkozyrev.data.repository.detailrepository.CityWeatherRepositoryImpl

class GetWeatherRemoteInteractor(private val cityWeatherRepositoryImpl: CityWeatherRepositoryImpl) {
    suspend fun getWeatherRemote(lat: Float, lon: Float): WeatherResponses =
        cityWeatherRepositoryImpl.getWeatherRemote(lat, lon)
}
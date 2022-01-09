package com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor

import com.example.weathermediasoftkozyrev.data.repository.detailrepository.CityWeatherRepositoryImpl

class CheckWeatherInDatabaseInteractor(private val cityWeatherRepositoryImpl: CityWeatherRepositoryImpl) {
    fun checkWeatherInDatabaseInteractor(id: Int) =
        cityWeatherRepositoryImpl.checkWeatherInDatabase(id)
}
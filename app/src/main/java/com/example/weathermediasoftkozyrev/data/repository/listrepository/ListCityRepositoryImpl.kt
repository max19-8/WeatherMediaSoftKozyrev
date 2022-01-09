package com.example.weathermediasoftkozyrev.data.repository.listrepository

import com.example.weathermediasoftkozyrev.data.db.WeatherDao


class ListCityRepositoryImpl(private val weatherDao: WeatherDao) : ListCityRepository {
    override fun getCities() = weatherDao.getCities()
}
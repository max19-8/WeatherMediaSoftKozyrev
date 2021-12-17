package com.example.weathermediasoftkozyrev.repository

import com.example.weathermediasoftkozyrev.db.WeatherDao


class ListCityRepository(private val weatherDao:WeatherDao) {
   fun getCities() = weatherDao.getCities()

}
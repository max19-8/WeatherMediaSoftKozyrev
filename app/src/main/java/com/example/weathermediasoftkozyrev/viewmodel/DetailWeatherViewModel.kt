package com.example.weathermediasoftkozyrev.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.weathermediasoftkozyrev.repository.CityWeatherRepository
import com.example.weathermediasoftkozyrev.resource.Resource
import kotlinx.coroutines.Dispatchers


class DetailWeatherViewModel(
    private val repository: CityWeatherRepository
): ViewModel() {

    fun getCityWeather(cityId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCityWeatherDescription(cityId = cityId)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }
}









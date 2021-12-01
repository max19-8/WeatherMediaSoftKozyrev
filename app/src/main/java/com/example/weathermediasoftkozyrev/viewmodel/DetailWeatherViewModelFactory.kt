package com.example.weathermediasoftkozyrev.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathermediasoftkozyrev.repository.CityWeatherRepository

class WeatherViewModelFactory constructor(private val repository: CityWeatherRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailWeatherViewModel::class.java)) {
            DetailWeatherViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
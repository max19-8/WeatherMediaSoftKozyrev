package com.example.weathermediasoftkozyrev.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.weathermediasoftkozyrev.db.CityEntity
import com.example.weathermediasoftkozyrev.db.WeatherDatabase
import com.example.weathermediasoftkozyrev.repository.ListCityRepository

class ListCityViewModel (
   application: Application
): AndroidViewModel(application) {
    private val repository:ListCityRepository

    init {
        val citiesDao = WeatherDatabase.getDatabase(application,viewModelScope,application.resources)
            .weatherDao()
        repository = ListCityRepository(citiesDao)
    }
     fun getListCities(): LiveData<List<CityEntity>> =
         repository.getCities()
}
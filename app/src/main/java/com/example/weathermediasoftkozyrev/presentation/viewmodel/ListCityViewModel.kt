package com.example.weathermediasoftkozyrev.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.weathermediasoftkozyrev.data.model.City
import com.example.weathermediasoftkozyrev.data.db.WeatherDatabase
import com.example.weathermediasoftkozyrev.domain.interactor.listinteractor.GetCityListInteractor
import com.example.weathermediasoftkozyrev.data.repository.listrepository.ListCityRepositoryImpl

class ListCityViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: ListCityRepositoryImpl
    private val getCityListInteractor: GetCityListInteractor

    init {
        val weatherDao =
            WeatherDatabase.getDatabase(application, viewModelScope, application.resources)
                .weatherDao()
        repository = ListCityRepositoryImpl(weatherDao)
        getCityListInteractor = GetCityListInteractor(repository)
    }

    fun getListCities(): LiveData<List<City>> =
        getCityListInteractor.getCities()
}




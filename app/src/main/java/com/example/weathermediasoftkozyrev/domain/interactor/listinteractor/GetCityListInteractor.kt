package com.example.weathermediasoftkozyrev.domain.interactor.listinteractor

import androidx.lifecycle.LiveData
import com.example.weathermediasoftkozyrev.data.model.City
import com.example.weathermediasoftkozyrev.data.repository.listrepository.ListCityRepositoryImpl

class GetCityListInteractor(private val listCityRepositoryImpl: ListCityRepositoryImpl) {
    fun getCities(): LiveData<List<City>> =
        listCityRepositoryImpl.getCities()
}
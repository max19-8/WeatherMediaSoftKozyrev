package com.example.weathermediasoftkozyrev.data.repository.listrepository

import androidx.lifecycle.LiveData
import com.example.weathermediasoftkozyrev.data.model.City

interface ListCityRepository {
    fun getCities(): LiveData<List<City>>
}
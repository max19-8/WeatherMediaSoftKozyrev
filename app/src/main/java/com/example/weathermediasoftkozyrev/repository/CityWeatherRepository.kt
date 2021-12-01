package com.example.weathermediasoftkozyrev.repository


import com.example.weathermediasoftkozyrev.api.RetrofitInstance
import com.example.weathermediasoftkozyrev.model.CityWeatherResponse
import com.example.weathermediasoftkozyrev.utils.Constants
import retrofit2.Response

class CityWeatherRepository{
     suspend fun getCityWeatherDescription(
        cityId: Int
    ): Response<CityWeatherResponse> = RetrofitInstance.apiService.getCityWeatherDescription(id = cityId, units = Constants.units, appId = Constants.API_KEY,lang = Constants.language)
}
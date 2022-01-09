package com.example.weathermediasoftkozyrev.data.repository.notificationrepository

import com.example.weathermediasoftkozyrev.data.api.RetrofitInstance
import com.example.weathermediasoftkozyrev.data.model.WeatherResponses
import com.example.weathermediasoftkozyrev.domain.utils.Constants

class NotifyRepositoryImpl : NotifyRepository {

    override suspend fun getNotify(lat: Float, lon: Float): WeatherResponses =
        RetrofitInstance.apiService.getDetail(
            lat = lat, lon = lon,
            exclude = Constants.exclude,
            units = Constants.units,
            lang = Constants.language,
            appId = Constants.API_KEY
        )
}
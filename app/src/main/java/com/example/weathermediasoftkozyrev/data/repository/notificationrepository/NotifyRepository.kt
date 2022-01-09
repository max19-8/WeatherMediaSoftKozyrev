package com.example.weathermediasoftkozyrev.data.repository.notificationrepository

import com.example.weathermediasoftkozyrev.data.model.WeatherResponses

interface NotifyRepository {
    suspend fun getNotify(lat: Float, lon: Float): WeatherResponses
}
package com.example.weathermediasoftkozyrev.repository

import com.example.weathermediasoftkozyrev.api.RetrofitInstance
import com.example.weathermediasoftkozyrev.utils.Constants

class NotifyRepository {

    suspend fun getNotify(lat: Float,lon:Float)  =
        RetrofitInstance.apiService.getDetail( lat = lat, lon = lon, exclude = Constants.exclude,units = Constants.units,lang = Constants.language,appId = Constants.API_KEY )
}
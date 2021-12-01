package com.example.weathermediasoftkozyrev.api

import com.example.weathermediasoftkozyrev.model.CityWeatherResponse
import com.example.weathermediasoftkozyrev.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET(Constants.ENDPOINT_CITY_WEATHER)
    suspend fun getCityWeatherDescription(
        @Query(Constants.QUERY_ID) id: Int,
        @Query(Constants.QUERY_UNITS) units: String,
        @Query(Constants.QUERY_APP_ID) appId: String,
        @Query(Constants.DEFAULT_LANG) lang: String
    ): Response<CityWeatherResponse>
}

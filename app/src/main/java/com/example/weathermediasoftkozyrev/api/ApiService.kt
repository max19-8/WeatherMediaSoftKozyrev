package com.example.weathermediasoftkozyrev.api

import com.example.weathermediasoftkozyrev.model.Responses

import com.example.weathermediasoftkozyrev.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @GET(Constants.ENDPOINT_CITY_WEATHER)
    suspend fun getDetail(
        @Query(Constants.QUERY_LAT) lat: Float,
        @Query(Constants.QUERY_LON) lon: Float,
        @Query(Constants.QUERY_EXCLUDE) exclude: String,
        @Query(Constants.QUERY_UNITS) units: String,
        @Query(Constants.DEFAULT_LANG) lang: String,
        @Query(Constants.QUERY_APP_ID) appId: String,
    ): Responses


}



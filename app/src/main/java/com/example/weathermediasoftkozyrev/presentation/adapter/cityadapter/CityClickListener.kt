package com.example.weathermediasoftkozyrev.presentation.adapter.cityadapter

interface CityClickListener {
    fun onCityClick(cityLat:Float,cityLon: Float,cityId:Int,cityName:String)
}
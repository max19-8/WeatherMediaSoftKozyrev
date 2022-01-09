package com.example.weathermediasoftkozyrev.presentation.adapter.cityadapter

interface SendLocationListener {
    fun sendLocation(cityLat:Float,cityLon: Float,cityName:String)
}
package com.example.weathermediasoftkozyrev.presentation.adapter.pageradapter

interface SendWeatherInfoListener {
    fun sendWeatherInfo(date:Int,tempDay:Double,tempMax:Double,tempMin:Double)
}
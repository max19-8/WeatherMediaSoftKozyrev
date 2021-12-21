package com.example.weathermediasoftkozyrev.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.weathermediasoftkozyrev.WeatherApplication
import com.example.weathermediasoftkozyrev.db.WeatherDatabase
import com.example.weathermediasoftkozyrev.model.Responses
import com.example.weathermediasoftkozyrev.repository.CityWeatherRepository
import com.example.weathermediasoftkozyrev.resource.Resource
import kotlinx.coroutines.*


class DetailWeatherViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: CityWeatherRepository

    init {
        val weatherDao =
            WeatherDatabase.getDatabase(application, viewModelScope, application.resources)
                .weatherDao()
        repository = CityWeatherRepository(weatherDao)
    }

    private val job = Job()
    private val vmScope = CoroutineScope(job + Dispatchers.Main)
    fun getList(lat: Float, lon: Float, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        if (hasInternetConnection()) {
            try {
                emit(Resource.success(data = repository.getWeather(id)))
                emit(Resource.success(data = repository.getDetail(lat = lat, lon = lon)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error"))
            }
        } else {
            emit(Resource.success(data = repository.getWeather(id)))
        }
    }






    fun saveWeather(respEntity: Responses?, cityId: Int) =
        vmScope.launch(Dispatchers.IO) {
            respEntity?.id = cityId
            repository.saveWeather(respEntity)
        }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<WeatherApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}









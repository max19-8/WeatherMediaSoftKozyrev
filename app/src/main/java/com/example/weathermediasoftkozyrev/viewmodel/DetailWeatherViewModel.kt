package com.example.weathermediasoftkozyrev.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.weathermediasoftkozyrev.WeatherApplication
import com.example.weathermediasoftkozyrev.db.RespEntity
import com.example.weathermediasoftkozyrev.db.WeatherDatabase
import com.example.weathermediasoftkozyrev.model.Responses
import com.example.weathermediasoftkozyrev.repository.CityWeatherRepository
import com.example.weathermediasoftkozyrev.resource.Resource
import com.example.weathermediasoftkozyrev.ui.DetailFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class DetailWeatherViewModel(application: Application
): AndroidViewModel(application) {
    private val repository: CityWeatherRepository

    init {
        val weatherDao = WeatherDatabase.getDatabase(application,viewModelScope,application.resources)
            .weatherDao()
        repository = CityWeatherRepository(weatherDao)
    }
        private val job = Job()
        private val vmScope = CoroutineScope(job + Dispatchers.Main)
        private val _response = MutableLiveData <RespEntity>()
        val response: LiveData <RespEntity>
        get() = _response
    fun getList(lat:Float, lon:Float) = liveData(Dispatchers.IO){
        if (hasInternetConnection()){
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.getDetail(lat = lat,lon = lon)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error"))
            }
        }else{
                emit(Resource.success(data =repository.getWeather(lat,lon)))
        }
        }

   fun saveWeather(respEntity: Responses?) = vmScope.launch {
     repository.saveWeather(respEntity)
       Log.d("VIEWMODELSAVE","SAVEDDDDD")
 }


//  private  fun getWeather(lat: Float, lon: Float) {
//      repository.getWeather()
//      Log.d("VIEWMODELSAVE","$lat,$lon)")
//  }



    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<WeatherApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork =   connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }




}









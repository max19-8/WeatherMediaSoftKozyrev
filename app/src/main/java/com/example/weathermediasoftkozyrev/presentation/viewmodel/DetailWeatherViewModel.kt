package com.example.weathermediasoftkozyrev.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.weathermediasoftkozyrev.data.model.WeatherResponses
import com.example.weathermediasoftkozyrev.data.db.WeatherDatabase
import com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor.CheckWeatherInDatabaseInteractor
import com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor.GetWeatherRemoteInteractor
import com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor.GetWeatherDatabaseInteractor
import com.example.weathermediasoftkozyrev.domain.interactor.detailinteractor.SaveWeatherInteractor
import com.example.weathermediasoftkozyrev.data.repository.detailrepository.CityWeatherRepositoryImpl
import com.example.weathermediasoftkozyrev.domain.utils.NetworkConnection
import com.example.weathermediasoftkozyrev.domain.utils.Event
import kotlinx.coroutines.*

class DetailWeatherViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: CityWeatherRepositoryImpl
    private val getWeatherDatabaseInteractor: GetWeatherDatabaseInteractor
    private val getWeatherRemoteInteractor: GetWeatherRemoteInteractor
    private val saveWeatherInteractor: SaveWeatherInteractor
    private val checkWeatherInDatabaseInteractor: CheckWeatherInDatabaseInteractor
    private var connect: Boolean
    private var liveDataUpdateUI = MutableLiveData<Boolean>()
    val getLiveDataUpdateUI: LiveData<Boolean>
        get() = liveDataUpdateUI
    private val visibilityProgress = MutableLiveData<Boolean>()
    val getVisibilityProgress: LiveData<Boolean>
        get() = visibilityProgress
    private val weatherResponse = MutableLiveData<Event<WeatherResponses>>()
    val getWeatherResponse: LiveData<Event<WeatherResponses>>
        get() = weatherResponse
    private val job = Job()
    private val vmScope = CoroutineScope(job + Dispatchers.Main)

    init {
        val weatherDao =
            WeatherDatabase.getDatabase(application, viewModelScope, application.resources)
                .weatherDao()
        repository = CityWeatherRepositoryImpl(weatherDao)
        connect = NetworkConnection.hasInternetConnection(application)
        getWeatherDatabaseInteractor = GetWeatherDatabaseInteractor(repository)
        getWeatherRemoteInteractor = GetWeatherRemoteInteractor(repository)
        saveWeatherInteractor = SaveWeatherInteractor(repository)
        checkWeatherInDatabaseInteractor = CheckWeatherInDatabaseInteractor(repository)
    }

    fun getDetailWeather(lat: Float, lon: Float, id: Int) = vmScope.launch(Dispatchers.IO) {
        Event.loading(data = null)
        val checkWeather = checkWeatherInDatabaseInteractor.checkWeatherInDatabaseInteractor(id)
        visibilityProgress.postValue(true)
        if (connect) {
            try {
                if (checkWeather) {
                    weatherResponse.postValue(Event.success(
                            getWeatherDatabaseInteractor.getWeatherDatabase(id)))
                    visibilityProgress.postValue(false)
                    weatherResponse.postValue(
                        Event.success(
                            getWeatherRemoteInteractor.getWeatherRemote(lat = lat, lon = lon)))
                } else {
                    weatherResponse.postValue(Event.success(
                            getWeatherRemoteInteractor.getWeatherRemote(lat = lat, lon = lon)))
                    visibilityProgress.postValue(false)
                }
            } catch (e: Exception) {
                weatherResponse.postValue(Event.error(null, e.message.toString()))
                liveDataUpdateUI.postValue(true)
            } } else {
            if (checkWeather) {
                weatherResponse.postValue(
                    Event.success(getWeatherDatabaseInteractor.getWeatherDatabase(id)))
                visibilityProgress.postValue(false)
            } else {
                visibilityProgress.postValue(false)
                liveDataUpdateUI.postValue(true)
            }
        }
    }

    fun saveWeather(respEntity: WeatherResponses?, cityId: Int) =
        vmScope.launch(Dispatchers.IO) {
            respEntity?.id = cityId
            saveWeatherInteractor.saveWeather(respEntity)
        }
}
package com.example.weathermediasoftkozyrev.db

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weathermediasoftkozyrev.R
import com.example.weathermediasoftkozyrev.model.Daily
import com.example.weathermediasoftkozyrev.model.Responses
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [CityEntity::class,Responses::class],version = 1,exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class WeatherDatabase:RoomDatabase(){
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope,resources: Resources): WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance }
            synchronized(this) {
                   val instance = Room.databaseBuilder(context.applicationContext,
                        WeatherDatabase::class.java,
                        "weather-db")
                        .addCallback(Callback(resources, scope))
                        .build()
                    INSTANCE = instance
                return instance
            }
        }
    }
    private class Callback(private val resources: Resources, private val scope: CoroutineScope):
        RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch{
                    val citiesDao = database.weatherDao()
                  prePopulateDatabase(citiesDao)
                }
            }
        }
        private suspend  fun prePopulateDatabase(citiesDao: WeatherDao) {
            val jsonString = resources.openRawResource(R.raw.city_list).bufferedReader().use {
                it.readText()
            }
            val typeToken = object : TypeToken<List<CityEntity>>() {}.type
            val citiesList = Gson().fromJson<List<CityEntity>>(jsonString, typeToken)
            citiesDao.insertAllCities(citiesList)
        }
    }
    }




package com.example.weathermediasoftkozyrev.domain.utils

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val QUERY_APP_ID = "appid"
    const val API_KEY = "c9e23806729fc7183b355dd7462cff8b"
    //   43a0c32904fac11860d03b87c47495e9
    //  765c97e50551201b348274233bed2631
    const val QUERY_LAT = "lat"
    const val QUERY_LON = "lon"
    const val QUERY_EXCLUDE = "exclude"
    const val exclude = "minutely,hourly"
    const val QUERY_UNITS = "units"
    const val units = "metric"
    const val DEFAULT_LANG = "lang"
    const val language = "ru"
    const val FORMAT_DATE = "dd.MM.yyyy"
    const val FORMAT_TIME = "HH:mm:ss"
    const val TYPE_INTENT = "text/plain"
    const val DATABASE_NAME = "weather-db"
}
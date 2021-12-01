package com.example.weathermediasoftkozyrev.mock

data class City (
    val name: String
)
val listCities = mutableListOf(City(name = "Moscow"),City(name = "Kazan"))
//А можно было всего этого с json  файлом не делать,
// а просто засунуть такой лист в ресайклер и все бы так же работало!!

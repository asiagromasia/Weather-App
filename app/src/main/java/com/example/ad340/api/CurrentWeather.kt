package com.example.ad340.api

import com.squareup.moshi.Json

data class CurrentWeather(
    @field:Json(name= "dt") val date: Long,
    val name: String,
    val coord: Coordinates,
    @field:Json(name= "main") val forecast: TodayForecast,
    val weather: List<WeatherDescription>

)
data class TodayForecast(
    val temp: Float
)
data class Coordinates(val lat: Float, val lon: Float)

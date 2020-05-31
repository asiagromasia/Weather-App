package com.example.ad340.api

import com.squareup.moshi.Json
//added description and icon date
 //data class Forecast(val temp: Float, val description: String, val icon: String,val date: Long) {
    data class Forecast(val temp: Float,val date: Long,val icon: String) {


    }

//data class Forecast(val temp: Float)
data class Coordinates(val lat: Float, val lon: Float)

data class CurrentWeather(
    val date: Long,
    val name: String,
    val coord: Coordinates,
    val description: String,
    val icon: String,
    @field:Json(name= "main") val forecast: Forecast
)
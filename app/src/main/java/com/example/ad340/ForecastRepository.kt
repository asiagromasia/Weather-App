package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String){
        val randomValues = List(10){Random.nextFloat().rem(100)* 100 }
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp, getTempDescription(temp))
        }
        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempDescription(temp: Float) : String {
        //return if(temp <75)"It's too cold" else "It's great!"  or
        return when (temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder that you may think"
            in 55f.rangeTo(66f) -> "its ok"
            in 66f.rangeTo(77f) -> "Sweet"
            in 77f.rangeTo(100f) -> "Way too hot!"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What it is Arizona?!!?"
            else -> "Doesn't compute"
        }
    }

}
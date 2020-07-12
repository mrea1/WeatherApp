package com.mrea.weatherapp.data

import com.mrea.weatherapp.domain.Weather
import com.mrea.weatherapp.domain.WeatherMapper

class WeatherRepository(private val weatherApi: WeatherApi, private val mapper: WeatherMapper) {

    private var weather: Weather? = null

    suspend fun getWeather(zipCode: String, refresh: Boolean): Weather {
        if (refresh) {
            fetchWeather(zipCode)
        } else {
            weather ?: fetchWeather(zipCode)
        }
        return weather!!
    }

    suspend fun fetchWeather(zipCode: String) {
        weather = weatherApi.getCurrentWeather(zipCode = zipCode).map(zipCode)
    }

    private fun GetWeatherResponse.map(zipCode: String): Weather = mapper.map(this to zipCode)
}
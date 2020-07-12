package com.mrea.weatherapp.domain

import com.mrea.weatherapp.data.WeatherRepository

class GetCurrentWeather(private val repository: WeatherRepository) {

    suspend fun run(zipCode: String, refresh: Boolean = false): Weather {
        return repository.getWeather(zipCode, refresh)
    }
}
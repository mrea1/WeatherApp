package com.mrea.weatherapp.domain

import com.mrea.weatherapp.data.WeatherRepository

class GetCurrentWeather(private val repository: WeatherRepository) {

    suspend fun run(zipCode: String, refresh: Boolean = false): Weather {
        val weather = repository.getWeather(zipCode, refresh)

        // todo: mrea 7/11/20 - review business requirements for error handling with Product Owner
        if (weather.city.isEmpty() || weather.state.isEmpty() || weather.description.isEmpty()) {
            throw InvalidNetworkResponse("The API response is missing required data")
        }

        return weather
    }

    data class InvalidNetworkResponse(override val message: String): Exception(message)
}
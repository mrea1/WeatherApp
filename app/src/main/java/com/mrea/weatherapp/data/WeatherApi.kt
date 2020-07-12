package com.mrea.weatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("report.json")
    suspend fun getCurrentWeather(
        @Query("product") product: String = "observation",
        @Query("zipcode") zipCode: String,
        @Query("metric") isMetric: Boolean = false,
        @Query("oneobservation") oneResult: Boolean = true
    ): GetWeatherResponse

}


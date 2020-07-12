package com.mrea.weatherapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetWeatherResponse(
    @Json(name = "observations") val observations: ObservationsResponse,
    @Json(name = "feedCreation") val feedCreation: String,
    @Json(name = "metric") val metric: Boolean
)

@JsonClass(generateAdapter = true)
data class ObservationsResponse(
    @Json(name = "location") val location: List<LocationResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class LocationResponse(
    @Json(name = "observation") val observation: List<ObservationResponse> = listOf(),
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "distance") val distance: Double,
    @Json(name = "timezone") val timezone: Int
)

@JsonClass(generateAdapter = true)
data class ObservationResponse(
    @Json(name = "daylight") val daylight: String,
    @Json(name = "description") val description: String,
    @Json(name = "skyInfo") val skyInfo: Int,
    @Json(name = "skyDescription") val skyDescription: String,
    @Json(name = "temperature") val temperature: Double,
    @Json(name = "temperatureDesc") val temperatureDesc: String,
    @Json(name = "comfort") val comfort: Double,
    @Json(name = "highTemperature") val highTemperature: String,
    @Json(name = "lowTemperature") val lowTemperature: String,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "dewPoint") val dewPoint: Double,
    @Json(name = "precipitation1H") val precipitation1H: String,
    @Json(name = "precipitation3H") val precipitation3H: String,
    @Json(name = "precipitation6H") val precipitation6H: String,
    @Json(name = "precipitation12H") val precipitation12H: String,
    @Json(name = "precipitation24H") val precipitation24H: String,
    @Json(name = "precipitationDesc") val precipitationDesc: String,
    @Json(name = "airInfo") val airInfo: String,
    @Json(name = "airDescription") val airDescription: String,
    @Json(name = "windSpeed") val windSpeed: String,
    @Json(name = "windDirection") val windDirection: String,
    @Json(name = "windDesc") val windDesc: String,
    @Json(name = "windDescShort") val windDescShort: String,
    @Json(name = "barometerPressure") val barometerPressure: Double,
    @Json(name = "barometerTrend") val barometerTrend: String,
    @Json(name = "visibility") val visibility: String,
    @Json(name = "snowCover") val snowCover: String,
    @Json(name = "icon") val icon: Int,
    @Json(name = "iconName") val iconName: String,
    @Json(name = "iconLink") val iconLink: String,
    @Json(name = "ageMinutes") val ageMinutes: Int,
    @Json(name = "activeAlerts") val activeAlerts: Int,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "distance") val distance: Double,
    @Json(name = "elevation") val elevation: Int,
    @Json(name = "utcTime") val utcTime: String
)
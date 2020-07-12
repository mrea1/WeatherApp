package com.mrea.weatherapp.domain

data class Weather(
    val isNight: Boolean,
    val zipCode: String,
    val weatherType: WeatherType,
    val description: String,
    val skyInfo: Int,
    val skyDescription: String,
    val temperature: Double,
    val temperatureDesc: String,
    val comfort: Double,
    val highTemperature: String,
    val lowTemperature: String,
    val humidity: Int,
    val dewPoint: Double,
    val precipitation1H: String,
    val precipitation3H: String,
    val precipitation6H: String,
    val precipitation12H: String,
    val precipitation24H: String,
    val precipitationDesc: String,
    val airInfo: String,
    val airDescription: String,
    val windSpeed: String,
    val windDirection: String,
    val windDesc: String,
    val windDescShort: String,
    val barometerPressure: Double,
    val barometerTrend: String,
    val visibility: String,
    val snowCover: String,
    val icon: Int,
    val iconName: String,
    val iconLink: String,
    val ageMinutes: Int,
    val activeAlerts: Int,
    val country: String,
    val state: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Double,
    val elevation: Int,
    val utcTime: String
)

sealed class WeatherType {
    object Rain : WeatherType()
    object Sunny : WeatherType()
    object Cloudy : WeatherType()
    object Snow : WeatherType()
    object Unknown : WeatherType()
}
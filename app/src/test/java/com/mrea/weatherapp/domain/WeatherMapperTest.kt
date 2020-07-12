package com.mrea.weatherapp.domain

import com.mrea.weatherapp.assertEquals
import com.mrea.weatherapp.buildMockWeather
import com.mrea.weatherapp.buildMockWeatherResponse
import org.junit.Before
import org.junit.Test

class WeatherMapperTest {

    lateinit var mapper: WeatherMapper

    @Before
    fun setUp() {
        mapper = WeatherMapper()
    }

    @Test
    fun `map should map weathertype correctly`() {
        var weather = mapper.map(buildMockWeatherResponse(skyInfo = 5) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Cloudy, skyInfo = 5))

        weather = mapper.map(buildMockWeatherResponse(skyInfo = 10) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Cloudy, skyInfo = 10))

        weather = mapper.map(buildMockWeatherResponse(skyInfo = 13) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Cloudy, skyInfo = 13))

        weather = mapper.map(buildMockWeatherResponse(skyInfo = 18) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Cloudy, skyInfo = 18))

        weather = mapper.map(buildMockWeatherResponse(skyInfo = 7) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Cloudy, skyInfo = 7))
        
        weather = mapper.map(buildMockWeatherResponse(skyInfo = 30) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Cloudy, skyInfo = 30))

        weather = mapper.map(buildMockWeatherResponse(skyInfo = 2) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Sunny, skyInfo = 2))
        weather = mapper.map(buildMockWeatherResponse(skyInfo = 12) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Sunny, skyInfo = 12))

        weather = mapper.map(buildMockWeatherResponse(skyInfo = 14) to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Sunny, skyInfo = 14))

        weather = mapper.map(buildMockWeatherResponse(precipitationDesc = "Raining") to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Rain, precipitationDesc = "Raining"))

        weather = mapper.map(buildMockWeatherResponse(precipitationDesc = "Snowing") to "19101")
        weather.assertEquals(buildMockWeather(zipCode = "19101", weatherType = WeatherType.Snow, precipitationDesc = "Snowing"))
    }
}
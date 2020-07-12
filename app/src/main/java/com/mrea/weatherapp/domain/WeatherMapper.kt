package com.mrea.weatherapp.domain

import com.mrea.weatherapp.data.GetWeatherResponse


class WeatherMapper : Mapper<Pair<GetWeatherResponse, String>, Weather> {

    companion object {
        val sunnyIndices = listOf(1..4, 7..7, 12..12, 14..14).flatten()
        val cloudyIndices = listOf(5..7, 9..11, 13..13, 15..23, 26..33).flatten()
        const val RAIN = "Rain"
        const val SNOW = "Snow"
        const val DAYLIGHT_ID = "D"
    }

    override fun map(input: Pair<GetWeatherResponse, String>): Weather {
        val (response, zipCode) = input

        return response.observations.location.first().observation.first().run {
            val weatherType = when {
                precipitationDesc.contains(RAIN, ignoreCase = true) -> WeatherType.Rain
                precipitationDesc.contains(SNOW, ignoreCase = true) -> WeatherType.Snow
                skyInfo in cloudyIndices -> WeatherType.Cloudy
                skyInfo in sunnyIndices -> WeatherType.Sunny
                else -> WeatherType.Unknown
            }

            Weather(
                windSpeed = windSpeed,
                weatherType = weatherType,
                precipitation1H = precipitation1H,
                isNight = daylight != DAYLIGHT_ID,
                description = description,
                dewPoint = dewPoint,
                activeAlerts = activeAlerts,
                distance = distance,
                airDescription = airDescription,
                precipitationDesc = precipitationDesc,
                skyDescription = skyDescription,
                temperatureDesc = temperatureDesc,
                barometerTrend = barometerTrend,
                humidity = humidity,
                latitude = latitude,
                longitude = longitude,
                windDesc = windDesc,
                windDescShort = windDescShort,
                skyInfo = skyInfo,
                snowCover = snowCover,
                state = state,
                airInfo = airInfo,
                ageMinutes = ageMinutes,
                barometerPressure = barometerPressure,
                windDirection = windDirection,
                elevation = elevation,
                precipitation3H = precipitation3H,
                precipitation6H = precipitation6H,
                precipitation12H = precipitation12H,
                comfort = comfort,
                highTemperature = highTemperature,
                lowTemperature = lowTemperature,
                iconLink = iconLink,
                icon = icon,
                precipitation24H = precipitation24H,
                iconName = iconName,
                temperature = temperature,
                city = city,
                country = country,
                utcTime = utcTime,
                visibility = visibility,
                zipCode = zipCode
            )
        }
    }
}
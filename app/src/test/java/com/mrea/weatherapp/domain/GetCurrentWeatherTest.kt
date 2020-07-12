package com.mrea.weatherapp.domain

import com.mrea.weatherapp.CoroutinesTestRule
import com.mrea.weatherapp.buildMockWeather
import com.mrea.weatherapp.data.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCurrentWeatherTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var usecase: GetCurrentWeather
    lateinit var repository: WeatherRepository

    @Test
    fun `usecase should return weather data from repository`() = runBlockingTest {
        repository = mockk {
            coEvery { getWeather(any(), any()) } returns buildMockWeather(
                city = "Philadelphia",
                state = "Pennsylvania",
                description = "Hello, world!"
            )
        }
        usecase = GetCurrentWeather(repository)

        usecase.run("12345", true)
        coVerify { repository.getWeather("12345", true) }

        usecase.run("", false)
        coVerify { repository.getWeather("", false) }
    }

    @Test(expected = GetCurrentWeather.InvalidNetworkResponse::class)
    fun `usecase should validate city`() = runBlockingTest {
        repository = mockk {
            coEvery { getWeather(any(), any()) } returns buildMockWeather(
                city = "Philadelphia",
                state = "",
                description = "Hello, world!"
            )
        }
        usecase = GetCurrentWeather(repository)
        usecase.run("12345", false)
    }

    @Test(expected = GetCurrentWeather.InvalidNetworkResponse::class)
    fun `usecase should validate state`() = runBlockingTest {
        repository = mockk {
            coEvery { getWeather(any(), any()) } returns buildMockWeather(
                city = "",
                state = "Pennsylvania",
                description = "Hello, world!"
            )
        }
        usecase = GetCurrentWeather(repository)
        usecase.run("12345", false)
    }

    @Test(expected = GetCurrentWeather.InvalidNetworkResponse::class)
    fun `usecase should validate description`() = runBlockingTest {
        repository = mockk {
            coEvery { getWeather(any(), any()) } returns buildMockWeather(
                city = "Philadelphia",
                state = "Pennsylvania",
                description = ""
            )
        }
        usecase = GetCurrentWeather(repository)
        usecase.run("12345", false)
    }
}
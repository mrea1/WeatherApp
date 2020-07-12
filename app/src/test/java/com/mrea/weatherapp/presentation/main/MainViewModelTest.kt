package com.mrea.weatherapp.presentation.main

import com.mrea.weatherapp.*
import com.mrea.weatherapp.domain.GetCurrentWeather
import com.mrea.weatherapp.domain.WeatherType
import com.mrea.weatherapp.presentation.AnimationUiState
import com.mrea.weatherapp.presentation.ImageUiState
import com.mrea.weatherapp.presentation.TextInputUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskRul = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var viewModel: MainViewModel
    lateinit var getCurrentWeather: GetCurrentWeather
    lateinit var app: WeatherApp

    @Before
    fun setUp() {
        getCurrentWeather = mockk {
            coEvery { run(any(), any()) } returns buildMockWeather()
        }
        app = mockk {
            every { getString(R.string.feels_like_temperature, any()) } answers { "Feels like ${secondArg<Array<Any>>().first()}°" }
            every { getString(R.string.temperature, any()) } answers { "${secondArg<Array<Any>>().first()}°" }
        }
    }

    @Test
    fun `when viewmodel is created, it should fetch weather data for Austin`() = runBlockingTest {
        viewModel = MainViewModel(app, getCurrentWeather)
        coVerify(exactly = 1) { getCurrentWeather.run("78704", false) }
    }

    @Test
    fun `the viewmodel initial state should be loading`() = runBlockingTest {
        viewModel = MainViewModel(app, getCurrentWeather)
        viewModel.initialState().isLoading.assertTrue()
    }

    @Test
    fun `when onError is called, the error UI should be displayed, and loading and animation should be hidden`() = runBlockingTest {
        getCurrentWeather = mockk {
            coEvery { run(any(), any()) } throws GetCurrentWeather.InvalidNetworkResponse("message")
        }
        viewModel = MainViewModel(app, getCurrentWeather)

        viewModel.onError(GetCurrentWeather.InvalidNetworkResponse("message"))
        viewModel.uiState.isLoading.assertFalse()
        viewModel.uiState.hasError.assertTrue()
        viewModel.uiState.animation.assertEquals(AnimationUiState(isVisible = false))
    }

    @Test
    fun `when data fetch is successful, the UI should display the current weather data`() = runBlockingTest {

        viewModel = MainViewModel(app, getCurrentWeather)
        viewModel.showWeatherData(
            weather = buildMockWeather(
                zipCode = "78704",
                city = "Austin",
                state = "Texas",
                isNight = false,
                weatherType = WeatherType.Rain,
                comfort = 75.0,
                temperature = 70.0,
                description = "Summertime and the livin' is easy",
                iconLink = "https://media.giphy.com/media/37nRXpCEP9H1f1WVrb/giphy.gif"
            )
        )

        viewModel.uiState.isLoading.assertFalse()
        viewModel.uiState.hasError.assertFalse()
        viewModel.uiState.searchBox.assertEquals(TextInputUiState(isVisible = false, hasError = false, currentText = "78704"))
        viewModel.uiState.currentLocation.assertEquals("Austin, Texas")
        viewModel.uiState.showNightMode.assertFalse()
        viewModel.uiState.animation.assertEquals(AnimationUiState(false, R.raw.rain))
        viewModel.uiState.feelsLikeTemperature.assertEquals("Feels like 75°")
        viewModel.uiState.currentTemperature.assertEquals("70°")
        viewModel.uiState.currentWeatherDescription.assertEquals("Summertime and the livin' is easy")
        viewModel.uiState.icon.assertEquals(ImageUiState(url = "https://media.giphy.com/media/37nRXpCEP9H1f1WVrb/giphy.gif"))
    }

    @Test
    fun `getBackgroundAnimation should map weather type to correct AnimationUiState`() = runBlockingTest {
        viewModel = MainViewModel(app, getCurrentWeather)
        viewModel.getBackgroundAnimation(WeatherType.Rain).assertEquals(AnimationUiState(false, R.raw.rain))
        viewModel.getBackgroundAnimation(WeatherType.Sunny).assertEquals(AnimationUiState(true, R.raw.sun))
        viewModel.getBackgroundAnimation(WeatherType.Cloudy).assertEquals(AnimationUiState(false, R.raw.cloudy))
        viewModel.getBackgroundAnimation(WeatherType.Snow).assertEquals(AnimationUiState(true, R.raw.snow))
        viewModel.getBackgroundAnimation(WeatherType.Unknown).assertEquals(AnimationUiState(false, 0, false))
    }
}
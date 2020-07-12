package com.mrea.weatherapp.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mrea.weatherapp.R
import com.mrea.weatherapp.WeatherApp
import com.mrea.weatherapp.domain.GetCurrentWeather
import com.mrea.weatherapp.domain.Weather
import com.mrea.weatherapp.domain.WeatherType
import com.mrea.weatherapp.presentation.AnimationUiState
import com.mrea.weatherapp.presentation.ImageUiState
import com.mrea.weatherapp.presentation.TextInputUiState
import com.mrea.weatherapp.presentation.gone
import com.mrea.weatherapp.util.isValidZip
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

// Setting default to Austin. Normally this app would use GPS to determine default
const val AUSTIN_ZIP_CODE = "78704"

class MainViewModel(application: Application, private val getCurrentWeather: GetCurrentWeather) : AndroidViewModel(application) {

    private val app: WeatherApp get() = getApplication()

    private val _uiStateLiveData: MutableLiveData<MainUiState> = MutableLiveData(
        MainUiState(isLoading = true)
    )
    val uiStateLiveData: LiveData<MainUiState> get() = _uiStateLiveData
    val uiState: MainUiState get() = requireNotNull(uiStateLiveData.value)

    var searchJob: Job? = null

    init {
        getData {
            showWeatherData(getCurrentWeather.run(AUSTIN_ZIP_CODE))
        }
    }

    private fun onError(error: Throwable) {
        Timber.e("$error")
        updateState(uiState.copy(hasError = true, isLoading = false, animation = gone()))
    }

    fun updateState(uiState: MainUiState) {
        _uiStateLiveData.value = uiState
    }

    fun showWeatherData(weather: Weather) {
        updateState(
            uiState.copy(
                isLoading = false,
                hasError = false,
                searchBox = TextInputUiState(isVisible = false, hasError = false, currentText = weather.zipCode),
                currentLocation = "${weather.city}, ${weather.state}",
                showNightMode = weather.isNight,
                animation = getBackgroundAnimation(weather.weatherType),
                feelsLikeTemperature = app.getString(R.string.feels_like_temperature, weather.comfort.toInt().toString()),
                currentTemperature = app.getString(R.string.temperature, weather.temperature.toInt().toString()),
                currentWeatherDescription = weather.description,
                icon = ImageUiState(url = weather.iconLink, debugLoggingEnabled = true)
            )
        )
    }

    fun getBackgroundAnimation(weatherType: WeatherType): AnimationUiState {
        return when (weatherType) {
            is WeatherType.Sunny -> AnimationUiState(true, R.raw.sun)
            is WeatherType.Cloudy -> AnimationUiState(false, R.raw.cloudy)
            is WeatherType.Rain -> AnimationUiState(false, R.raw.rain)
            is WeatherType.Snow -> AnimationUiState(true, R.raw.snow)
            is WeatherType.Unknown -> gone()
        }
    }

    fun getData(fetch: suspend () -> Unit) {
        updateState(uiState.copy(isLoading = true, animation = gone(), hasError = false))
        viewModelScope.launch {
            runCatching { fetch() }.getOrElse(::onError)
        }
    }

    fun onRefresh() {
        getData {
            showWeatherData(getCurrentWeather.run(uiState.searchBox.currentText, refresh = true))
        }
    }

    fun onAnimationChanged(weatherType: WeatherType) {
        updateState(uiState.copy(animation = getBackgroundAnimation(weatherType)))
    }

    fun onSearch() {
        updateState(uiState.copy(searchBox = uiState.searchBox.copy(isVisible = true), currentLocation = ""))
    }


    fun onSearchTextChanged(newText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            updateState(uiState.copy(searchBox = uiState.searchBox.copy(hasError = false)))
            delay(1500)

            if (newText.isNotEmpty()) {
                if (newText.isValidZip()) {
                    getData {
                        showWeatherData(getCurrentWeather.run(newText, refresh = true))
                    }
                } else {
                    updateState(
                        uiState.copy(
                            searchBox = uiState.searchBox.copy(
                                hasError = true,
                                errorText = app.getString(R.string.search_error_text)
                            )
                        )
                    )
                }
            }

        }
    }
}


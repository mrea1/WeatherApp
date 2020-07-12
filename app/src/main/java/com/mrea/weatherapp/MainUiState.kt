package com.mrea.weatherapp

import com.mrea.weatherapp.presentation.AnimationUiState
import com.mrea.weatherapp.presentation.ImageUiState
import com.mrea.weatherapp.presentation.TextInputUiState
import com.mrea.weatherapp.presentation.gone

data class MainUiState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val showNightMode: Boolean = false,
    val currentLocation: String = "",
    val currentWeatherDescription: String = "",
    val currentTemperature: String = "",
    val feelsLikeTemperature: String = "",
    val searchBox: TextInputUiState = gone(),
    val icon: ImageUiState = gone(),
    val animation: AnimationUiState = gone()
) {

    val showContent: Boolean get() = isLoading.not() && hasError.not()

    val showKeyboard: Boolean
        get() = when {
            hasError || isLoading || searchBox.isVisible.not() -> false
            searchBox.isVisible -> true
            else -> false
        }
}
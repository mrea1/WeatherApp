package com.mrea.weatherapp.presentation.main

import com.mrea.weatherapp.assertEmpty
import com.mrea.weatherapp.assertFalse
import com.mrea.weatherapp.assertTrue
import com.mrea.weatherapp.presentation.AnimationUiState
import com.mrea.weatherapp.presentation.ImageUiState
import com.mrea.weatherapp.presentation.TextInputUiState
import junit.framework.Assert.assertEquals
import org.junit.Test

class MainUiStateTest {

    lateinit var mainUiState: MainUiState

    @Test
    fun `default UiState values should be correct`() {
        mainUiState = MainUiState()
        mainUiState.feelsLikeTemperature.assertEmpty()
        mainUiState.currentWeatherDescription.assertEmpty()
        mainUiState.showNightMode.assertFalse()
        mainUiState.currentTemperature.assertEmpty()
        mainUiState.currentLocation.assertEmpty()
        mainUiState.hasError.assertFalse()
        mainUiState.isLoading.assertFalse()
        assertEquals(mainUiState.searchBox, TextInputUiState())
        assertEquals(mainUiState.icon, ImageUiState())
        assertEquals(mainUiState.animation, AnimationUiState())
    }

    @Test
    fun `content should only show if the screen is not loading and does not have an error`() {
        mainUiState = MainUiState(isLoading = false, hasError = false)
        mainUiState.showContent.assertTrue()

        mainUiState = MainUiState(isLoading = true, hasError = false)
        mainUiState.showContent.assertFalse()

        mainUiState = MainUiState(isLoading = false, hasError = true)
        mainUiState.showContent.assertFalse()
    }

    @Test
    fun `keyboard should not show the screen is loading, has an error, or the search box is not visible`() {
        mainUiState = MainUiState(isLoading = true)
        mainUiState.showKeyboard.assertFalse()

        mainUiState = MainUiState(hasError = true)
        mainUiState.showKeyboard.assertFalse()

        mainUiState = MainUiState(searchBox = TextInputUiState(isVisible = false))
        mainUiState.showKeyboard.assertFalse()
    }

    @Test
    fun `keyboard should show if the searchbox is visible`() {
        mainUiState = MainUiState(searchBox = TextInputUiState(isVisible = true))
        mainUiState.showKeyboard.assertTrue()
    }
}
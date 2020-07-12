package com.mrea.weatherapp.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class ImageUiState(
    val url: String = "",
    @DrawableRes val localImage: Int = 0,
    val isVisible: Boolean = url.isNotEmpty() || localImage != 0,
    val crossfadeEnabled: Boolean = true,
    val crossfadeDuration: Int = 500,
    val debugLoggingEnabled: Boolean = false,
    val roundedCorners: Boolean = false,
    val roundedCornerRadius: Int = 10
)

data class AnimationUiState(
    val loopEnabled: Boolean = false,
    @RawRes val resourceId: Int = 0,
    val isVisible: Boolean = resourceId != 0
)

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T> gone(): T = when (T::class) {
    AnimationUiState::class -> AnimationUiState(isVisible = false)
    ImageUiState::class -> ImageUiState(isVisible = false)
    TextInputUiState::class -> TextInputUiState(isVisible = false)
    else -> throw IllegalArgumentException("Unknown type ${T::class.simpleName}")
} as T

data class TextInputUiState(
    val isVisible: Boolean = false,
    val hasError: Boolean = false,
    val currentText: String = "",
    val errorText: String = ""
)
package com.mrea.weatherapp.util

fun String.isValidZip() = "^\\d{5}(?:[-\\s]\\d{4})?\$".toRegex().matches(this)

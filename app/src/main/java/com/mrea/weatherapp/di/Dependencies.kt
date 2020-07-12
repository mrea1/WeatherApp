package com.mrea.weatherapp.di

import com.mrea.weatherapp.MainViewModel
import com.mrea.weatherapp.data.AuthInterceptor
import com.mrea.weatherapp.data.WeatherApi
import com.mrea.weatherapp.data.WeatherRepository
import com.mrea.weatherapp.domain.GetCurrentWeather
import com.mrea.weatherapp.domain.WeatherMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

const val WEATHER_API_BASE_URL = "https://weather.ls.hereapi.com/weather/1.0/"

val module = module {
    viewModel { MainViewModel(androidApplication(), get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    single {
        Retrofit.Builder().baseUrl(WEATHER_API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }

    single<WeatherApi> { get<Retrofit>().create() }

    factory { WeatherMapper() }
    single { WeatherRepository(get(), get()) }
    single { GetCurrentWeather(get()) }
}
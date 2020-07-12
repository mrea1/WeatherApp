package com.mrea.weatherapp.data

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

object AuthInterceptor : Interceptor {
    const val API_KEY_NAME: String = "apiKey"
    const val API_KEY: String = "QK_yM83wVXTpObnyknsVr1Uq7brPnNOYSjFM3-WrRSQ"

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().let { request ->
                val url: HttpUrl = request.url
                    .newBuilder()
                    .addQueryParameter(API_KEY_NAME, API_KEY)
                    .build()

                request
                    .newBuilder()
                    .url(url)
                    .build()
            }
        )
    }
}
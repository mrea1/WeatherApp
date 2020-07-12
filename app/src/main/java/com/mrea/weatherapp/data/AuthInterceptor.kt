package com.mrea.weatherapp.data

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

object AuthInterceptor : Interceptor {

    // Using API key-based authentication to save time.
    // A more secure approach would be OAuth2, which the Here API does support

    // Additionally, some form of protection against man-in-the-middle attacks should be used,
    // such as SSL Pinning or Certificate Transparency
    private const val API_KEY_NAME: String = "apiKey"
    private const val API_KEY: String = "QK_yM83wVXTpObnyknsVr1Uq7brPnNOYSjFM3-WrRSQ"

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
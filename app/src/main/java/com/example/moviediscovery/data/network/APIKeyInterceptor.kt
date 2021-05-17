package com.example.moviediscovery.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class APIKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val originalRequest = chain.request()
            .newBuilder()
            .url(url)
            .build()

        Log.d("APIKeyInterceptor", originalRequest.url().toString())

        return chain.proceed(originalRequest)
    }
}
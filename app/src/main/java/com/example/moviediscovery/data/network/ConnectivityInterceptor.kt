package com.example.moviediscovery.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.moviediscovery.exception.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val context: Context) : Interceptor {
    private fun isConnected(): Boolean {
        val connectivityManager = context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected()) {
            throw NoNetworkException()
        }
        return chain.proceed(originalRequest)
    }
}
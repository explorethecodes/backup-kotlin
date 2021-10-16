package com.oneindia.journovideos.base.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.oneindia.journovideos.base.network.exceptions.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have an active data connection")

        var request = chain.request()
        request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()

        return chain.proceed(request)
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//        connectivityManager?.let {
//            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
//                result = when {
//                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                    else -> false
//                }
//            }
//        }
        return result
    }

}
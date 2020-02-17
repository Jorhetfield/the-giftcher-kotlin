package com.example.thegiftcherk.setup.network

import android.util.Log
import com.example.thegiftcherk.setup.Prefs
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class CustomInterceptor(private val prefs: Prefs) : Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(Interceptor::class.java.simpleName, "Authorization: ${prefs.token}")

        val request = chain.request().newBuilder()
        request.addHeader(HttpHeaders.ACCEPT, "application/json")
        prefs.token?.let { authToken ->
            request
                .addHeader("Authorization", authToken)
                .addHeader("Lang", Locale.getDefault().language)
                .build()
        }

        return chain.proceed(request.build())
    }
}
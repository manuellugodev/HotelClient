package com.manuellugodev.hotelmanagment.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val username: String, private val password: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val credential = Credentials.basic(username, password)
        val authenticadRequest = chain.request().newBuilder()
            .header("Authorization", credential)
            .build()
        return chain.proceed(authenticadRequest)
    }
}
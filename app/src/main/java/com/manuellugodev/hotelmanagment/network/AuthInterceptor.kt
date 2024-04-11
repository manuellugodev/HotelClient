package com.manuellugodev.hotelmanagment.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticadRequest = chain.request().newBuilder()
            .header("Authorization", "Bearer ${token.getToken()}")
            .build()
        return chain.proceed(authenticadRequest)
    }
}

interface TokenProvider {
    fun getToken(): String
}
package com.manuellugodev.hotelmanagment.framework.network

import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: TokenManagment) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticadRequest = chain.request().newBuilder()
            .header("Authorization", "Bearer ${token.getToken()}")
            .build()
        val response = chain.proceed(authenticadRequest)


        if (response.code == 401) {
            val data = response.body?.string()
            if (data!!.contains("ExpiredJwtException")) {
                token.removeToken()
            }
        }
        return response
    }
}

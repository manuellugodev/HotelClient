package com.manuellugodev.hotelmanagment.framework.network.request

import com.manuellugodev.hotelmanagment.framework.network.service.LoginService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginRequest(private val baseUrl: String) {

    private val okHttpClient: OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().build()
    }

    fun getService(): LoginService {
        return buildRetrofit().create(LoginService::class.java)
    }

    fun buildRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}
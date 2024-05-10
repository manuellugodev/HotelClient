package com.manuellugodev.hotelmanagment.framework.network

import com.manuellugodev.hotelmanagment.domain.TokenManagment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRequest<T : Any>(
    private val baseUrl: String,
    private val token: TokenManagment
) {

    private val okHttpClient: OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(
            com.manuellugodev.hotelmanagment.framework.network.AuthInterceptor(
                token
            )
        ).build()
    }

    inline fun <reified T : Any> getService(): T =
        buildRetrofit().run {
            this.create(T::class.java)
        }


    fun buildRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


}


package com.manuellugodev.hotelmanagment.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRequest<T : Any>(
    private val baseUrl: String,
    private val username: String,
    private val password: String
) {

    private val okHttpClient: OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(AuthInterceptor(username, password)).build()
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
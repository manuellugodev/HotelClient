package com.manuellugodev.hotelmanagment.framework.network.service

import com.manuellugodev.hotelmanagment.framework.network.entities.LoginRequestBody
import com.manuellugodev.hotelmanagment.framework.network.entities.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/login")
    suspend fun doLogin(@Body authRequest: LoginRequestBody): Response<LoginResponse>
}
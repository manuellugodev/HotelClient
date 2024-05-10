package com.manuellugodev.hotelmanagment.framework.network.service

import com.manuellugodev.hotelmanagment.framework.network.entities.ProfileApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("/user/{username}")
    suspend fun getProfileData(@Path("username") username: String): Response<ProfileApi>

}
package com.manuellugodev.hotelmanagment.network.service

import com.manuellugodev.hotelmanagment.features.profile.Profile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("/profile/{username}")
    suspend fun getProfileData(@Path("username") username: String): Response<Profile>

}
package com.manuellugodev.hotelmanagment.network.service

import com.manuellugodev.hotelmanagment.network.entities.RoomApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RoomService {

    @GET("/rooms")
    suspend fun getAllRoomsAvailable(@Query("available")available:Boolean):Response<List<RoomApi>>
}
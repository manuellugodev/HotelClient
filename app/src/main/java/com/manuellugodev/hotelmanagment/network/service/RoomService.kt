package com.manuellugodev.hotelmanagment.network.service

import com.manuellugodev.hotelmanagment.network.entities.RoomApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RoomService {

    @GET("/rooms")
    suspend fun getAllRoomsAvailable(
        @Query("available") available: Boolean,
        @Query("dStartTime") desiredStartTime: String,
        @Query("dEndTime") desiredEndTime: String,
        @Query("guests") guestNumber: Int
    ): Response<List<RoomApi>>
}
package com.manuellugodev.hotelmanagment.network.service

import com.manuellugodev.hotelmanagment.network.entities.RoomApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface RoomService {

    @GET("/rooms")
    suspend fun getAllRoomsAvailable(@Query("available")available:Boolean,@Query("dStartTime") desiredStartTime:Date,@Query("dEndTime") desiredEndTime:Date,):Response<List<RoomApi>>
}
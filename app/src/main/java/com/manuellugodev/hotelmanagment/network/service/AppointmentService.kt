package com.manuellugodev.hotelmanagment.network.service

import com.manuellugodev.hotelmanagment.network.entities.Appointment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date

interface AppointmentService {

    @GET("/appointment")
    suspend fun getAppointments(): Response<List<Appointment>>

    @POST("/appointment")
    suspend fun sendAppointment(
        @Query("guestId") guestId: Int,
        @Query("roomId") roomId: Int,
        @Query("startTime") startTime: Date, // Represented as Date
        @Query("endTime") endTime: Date,     // Represented as Date
        @Query("purpose") purpose: String
    ): Response<String>
}
package com.manuellugodev.hotelmanagment.framework.network.service

import com.manuellugodev.hotelmanagment.framework.network.entities.ApiResponse
import com.manuellugodev.hotelmanagment.framework.network.entities.Appointment
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentService {

    @GET("/appointment")
    suspend fun getAppointments(): Response<ApiResponse<List<Appointment>>>

    @POST("/appointment")
    suspend fun sendAppointment(
        @Query("guestId") guestId: Int,
        @Query("roomId") roomId: Int,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String,
        @Query("purpose") purpose: String,
        @Query("total") total: Double
    ): Response<String>

    @GET("/appointment/guest/{idGuest}")
    suspend fun getMyAppointments(@Path("idGuest") idGuest: Int): Response<ApiResponse<List<Appointment>>>

    @GET("/appointment/guest/{idGuest}/upcoming")
    suspend fun getUpcomingAppointments(
        @Path("idGuest") idGuest: Int,
        @Query("dStartTime") date: String
    ): Response<ApiResponse<List<Appointment>>>

    @GET("/appointment/guest/{idGuest}/past")
    suspend fun getPastAppointments(
        @Path("idGuest") idGuest: Int,
        @Query("dStartTime") date: String
    ): Response<ApiResponse<List<Appointment>>>

    @DELETE("/appointment")
    suspend fun deleteAppointment(@Query("id") appointmentId: Int): Response<ApiResponse<String>>
}
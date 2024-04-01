package com.manuellugodev.hotelmanagment.network.service

import com.manuellugodev.hotelmanagment.network.entities.Appointment
import retrofit2.Response
import retrofit2.http.GET

interface AppointmentService {

    @GET("/appointment")
    suspend fun getAppointments():Response<List<Appointment>>
}
package com.manuellugodev.hotelmanagment.network.request

data class AppointmentBody(
    val guestId: Int,
    val roomId: Int,
    val startTime: String,
    val endTime: String,
    val purpose: String
)

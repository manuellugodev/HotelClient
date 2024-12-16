package com.manuellugodev.hotelmanagment.framework.network.request

data class AppointmentBody(
    val guestId: Int,
    val roomId: Int,
    val startTime: String,
    val endTime: String,
    val purpose: String,
    val total: Double
)

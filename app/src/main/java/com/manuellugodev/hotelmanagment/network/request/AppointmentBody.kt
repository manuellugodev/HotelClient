package com.manuellugodev.hotelmanagment.network.request

import java.util.Date

data class AppointmentBody(
    val guestId: Int,
    val roomId: Int,
    val startTime: Date,
    val endTime: Date,
    val purpose: String
)

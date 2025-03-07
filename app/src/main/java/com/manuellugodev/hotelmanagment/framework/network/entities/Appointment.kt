package com.manuellugodev.hotelmanagment.framework.network.entities

data class Appointment(
    val appointmentId:Int,
    val guest:GuestApi,
    val room:RoomApi,
    val startTime:String,
    val endTime:String,
    val purpose:String,
    val status: String,
    val total: Double

)

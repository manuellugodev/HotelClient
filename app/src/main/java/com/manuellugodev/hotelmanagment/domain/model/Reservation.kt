package com.manuellugodev.hotelmanagment.domain.model

data class Reservation(
    var id:String?=null,
    val client:Customer,
    val roomHotel: RoomHotel,
    val checkIn:Long,
    val checkOut:Long,
    val price:Int,
    val taxPrice:Int,
    val totalPrice:Int
)

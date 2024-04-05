package com.manuellugodev.hotelmanagment.domain.model


data class Reservation(
    var id:Int,
    val client:Customer,
    val roomHotel: RoomHotel,
    val checkIn:Long,
    val checkOut:Long,
    val price:Double,
    val taxPrice:Double,
    val totalPrice:Double
)

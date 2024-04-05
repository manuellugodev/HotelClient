package com.manuellugodev.hotelmanagment.domain.model


data class RoomHotel(
    val id: Long,
    val description:String,
    val roomType:String,
    val pathImage:String,
    val peopleQuantity:Int,
    val price:Double

)

package com.manuellugodev.hotelmanagment.domain.model

import java.net.IDN

data class RoomHotel(
    val id: Long,
    val title:String,
    val numberOfBeds:Int,
    val pathImage:String,
    val peopleQuantity:Int,
    val price:Double

)

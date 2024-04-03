package com.manuellugodev.hotelmanagment.network.entities

data class RoomApi(
    val id: Int,
    val roomNumber: String ,
    val roomType: String ,
    val capacity: Int ,
    val description: String ,
    val price :Double,
    val image: String?
)

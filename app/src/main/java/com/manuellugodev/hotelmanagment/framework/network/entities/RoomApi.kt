package com.manuellugodev.hotelmanagment.framework.network.entities

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

data class RoomApi(
    val id: Int,
    val roomNumber: String,
    val roomType: String,
    val capacity: Int,
    val description: String,
    val price: Double,
    val image: String?
)

fun RoomApi.toRoomHotel(): RoomHotel {
    return RoomHotel(id.toLong(), description, roomType, image.toString(), capacity, price)
}

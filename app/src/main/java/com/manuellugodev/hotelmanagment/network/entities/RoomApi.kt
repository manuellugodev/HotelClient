package com.manuellugodev.hotelmanagment.network.entities

data class RoomApi(
    var id: Int = 0,
    var roomNumber: String = "",
    var roomType: String = "",
    var capacity: Int = 0,
    var description: String = ""
)

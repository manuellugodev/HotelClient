package com.manuellugodev.hotelmanagment.features.rooms.utils

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

sealed class RoomTypeState() {
    class Success(val data: List<RoomHotel>) : RoomTypeState()
    object Pending : RoomTypeState()
    class Error(val message: String) : RoomTypeState()
    class RoomSelected(val reservationId: Long) : RoomTypeState()
}

package com.manuellugodev.hotelmanagment.features.rooms.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel

sealed class RoomTypeEvent {

    data class OnClickRoomSelected(val room: RoomHotel):RoomTypeEvent()
    object DismissError:RoomTypeEvent()
}
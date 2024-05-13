package com.manuellugodev.hotelmanagment.features.rooms.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel

sealed class RoomTypeEvent {

    data class OnClickRoomSelected(val desiredStartTime: Long, val desiredEndTime: Long, val guests: Int, val room: RoomHotel):RoomTypeEvent()
    object DismissError:RoomTypeEvent()

    data class SearchRooms(val name:String):RoomTypeEvent()
}
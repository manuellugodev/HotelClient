package com.manuellugodev.hotelmanagment.features.rooms.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel

data class RoomTypeState(
    val showRooms:List<RoomHotel> = emptyList(),
    val showError:String="",
    val showLoader:Boolean = false,
    val navigateToBookId:Long=-1L,
)

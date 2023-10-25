package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.NumberGuest
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

interface RoomRepository {

    suspend fun searchRooms(guests: Int): DataResult<List<RoomHotel>>
}
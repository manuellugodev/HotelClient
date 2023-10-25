package com.manuellugodev.hotelmanagment.data.sources

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

interface RoomDataSource {
    suspend fun searchRooms():DataResult<List<RoomHotel>>
}
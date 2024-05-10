package com.manuellugodev.hotelmanagment.features.rooms.data

import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import java.util.Date

interface RoomDataSource {
    suspend fun searchRooms(): DataResult<List<RoomHotel>>
    suspend fun searchRooms(
        desiredStartTime: Date,
        desiredEndTime: Date,
        guests: Int
    ): DataResult<List<RoomHotel>>
}
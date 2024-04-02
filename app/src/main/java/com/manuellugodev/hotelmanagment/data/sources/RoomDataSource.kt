package com.manuellugodev.hotelmanagment.data.sources

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import java.util.Date

interface RoomDataSource {
    suspend fun searchRooms():DataResult<List<RoomHotel>>
    suspend fun searchRooms(
        desiredStartTime: Date,
        desiredEndTime: Date,
        guests:Int
    ): DataResult<List<RoomHotel>>
}
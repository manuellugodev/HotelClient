package com.manuellugodev.hotelmanagment.features.rooms.data

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.domain.utils.DataResult
import java.util.Date

interface RoomRepository {

    suspend fun searchRoomsAvailable(guests: Int): DataResult<List<RoomHotel>>
    suspend fun searchRoomsAvailable(desiredStarTime: Date, desiredEndTime: Date,guests: Int): DataResult<List<RoomHotel>>
}
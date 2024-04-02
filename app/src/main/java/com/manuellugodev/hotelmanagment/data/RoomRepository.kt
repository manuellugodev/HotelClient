package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.NumberGuest
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import java.util.Date

interface RoomRepository {

    suspend fun searchRoomsAvailable(guests: Int): DataResult<List<RoomHotel>>
    suspend fun searchRoomsAvailable(desiredStarTime: Date, desiredEndTime: Date): DataResult<List<RoomHotel>>
}
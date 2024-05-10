package com.manuellugodev.hotelmanagment.features.rooms.data

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.domain.utils.DataResult
import java.util.Date

class RoomRepositoryImpl(val dataSource: RoomDataSource) : RoomRepository {
    override suspend fun searchRoomsAvailable(guests: Int): DataResult<List<RoomHotel>> {

        return dataSource.searchRooms()

    }

    override suspend fun searchRoomsAvailable(
        desiredStarTime: Date,
        desiredEndTime: Date,
        guests: Int
    ): DataResult<List<RoomHotel>> {
        return dataSource.searchRooms(desiredStarTime, desiredEndTime,guests)
    }
}
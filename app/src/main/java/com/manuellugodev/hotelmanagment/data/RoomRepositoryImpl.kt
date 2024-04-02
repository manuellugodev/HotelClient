package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
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
package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class RoomRepositoryImpl(val dataSource: RoomDataSource) : RoomRepository {
    override suspend fun searchRooms(guests: Int): DataResult<List<RoomHotel>> {

        return dataSource.searchRooms()

    }
}
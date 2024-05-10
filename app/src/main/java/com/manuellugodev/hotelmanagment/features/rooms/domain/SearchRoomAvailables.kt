package com.manuellugodev.hotelmanagment.features.rooms.domain

import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomRepository
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import java.util.Date

class SearchRoomAvailables(val roomRepository: RoomRepository) {

    suspend operator fun invoke(desiredStartTime: Date,desiredEndTime: Date,guests:Int): DataResult<List<RoomHotel>> {

        return  roomRepository.searchRoomsAvailable(desiredStartTime,desiredEndTime,guests)

    }

}
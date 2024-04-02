package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.data.RoomRepository
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import java.util.Date

class SearchRoomAvailables(val roomRepository: RoomRepository) {

    suspend operator fun invoke(desiredStartTime: Date,desiredEndTime: Date,guests:Int): DataResult<List<RoomHotel>> {

        return  roomRepository.searchRoomsAvailable(desiredStartTime,desiredEndTime,guests)

    }

}
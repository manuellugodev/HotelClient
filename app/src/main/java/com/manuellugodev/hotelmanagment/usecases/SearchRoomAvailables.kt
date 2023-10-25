package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.data.RoomRepository
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class SearchRoomAvailables(val roomRepository: RoomRepository) {

    suspend operator fun invoke(guests:Int): DataResult<List<RoomHotel>> {

        return  roomRepository.searchRooms(guests)

    }

}
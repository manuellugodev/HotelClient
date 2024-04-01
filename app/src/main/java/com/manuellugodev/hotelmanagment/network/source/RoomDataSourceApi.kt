package com.manuellugodev.hotelmanagment.network.source

import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.network.entities.RoomApi
import com.manuellugodev.hotelmanagment.network.request.RoomRequest
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class RoomDataSourceApi(private val request: RoomRequest) : RoomDataSource {
    override suspend fun searchRooms(): DataResult<List<RoomHotel>> {

        return try {
            val result = request.service.getAllRoomsAvailable(true)

            if (result.isSuccessful) {
                DataResult.Success(result.body()!!.map { it.toRoomHotel() })
            } else {
                DataResult.Error(Exception("Error DataSOurce Room APi"))

            }
        } catch (e: Exception) {
            DataResult.Error(Exception("Error DataSOurce Room APi"))

        }

    }
}

fun RoomApi.toRoomHotel(): RoomHotel {
    return RoomHotel(this.id.toLong(), this.description, this.roomType, "", 3, this.price)
}

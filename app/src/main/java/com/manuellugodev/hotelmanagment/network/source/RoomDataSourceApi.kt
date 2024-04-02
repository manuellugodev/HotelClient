package com.manuellugodev.hotelmanagment.network.source

import android.util.Log
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.network.entities.RoomApi
import com.manuellugodev.hotelmanagment.network.request.RoomRequest
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import java.util.Date

class RoomDataSourceApi(private val request: RoomRequest) : RoomDataSource {
    override suspend fun searchRooms(): DataResult<List<RoomHotel>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchRooms(desiredStartTime:Date,desiredEndTime:Date,guests:Int): DataResult<List<RoomHotel>> {

        return try {
            val result = request.service.getAllRoomsAvailable(true,desiredStartTime,desiredEndTime,guests)

            if (result.isSuccessful) {
                DataResult.Success(result.body()!!.map { it.toRoomHotel() })
            } else {
                DataResult.Error(Exception("Error DataSource Room APi"))

            }
        } catch (e: Exception) {
            Log.e("ROOM ERROR:",e.message.toString())
            DataResult.Error(Exception("Error DataSource Room APi"))


        }

    }
}

fun RoomApi.toRoomHotel(): RoomHotel {
    return RoomHotel(this.id.toLong(), this.description, this.roomType, "", 3, this.price)
}

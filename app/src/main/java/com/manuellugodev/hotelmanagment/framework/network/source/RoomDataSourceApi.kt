package com.manuellugodev.hotelmanagment.framework.network.source

import android.util.Log
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomDataSource
import com.manuellugodev.hotelmanagment.framework.network.entities.RoomApi
import com.manuellugodev.hotelmanagment.framework.network.request.RoomRequest
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

class RoomDataSourceApi(private val request: RoomRequest) : RoomDataSource {
    override suspend fun searchRooms(): DataResult<List<RoomHotel>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchRooms(
        desiredStartTime: Date,
        desiredEndTime: Date,
        guests: Int
    ): DataResult<List<RoomHotel>> {

        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val result = request.service.getAllRoomsAvailable(
                true,
                sdf.format(desiredStartTime),
                sdf.format(desiredEndTime),
                guests
            )

            if (result.isSuccessful) {

                val date1 = Instant.ofEpochMilli(desiredStartTime.time).atZone(ZoneId.systemDefault()).toLocalDate()
                val date2 = Instant.ofEpochMilli(desiredEndTime.time).atZone(ZoneId.systemDefault()).toLocalDate()

                val daysBetween = ChronoUnit.DAYS.between(date1, date2)
                DataResult.Success(result.body()!!.map { it.toRoomHotel(daysBetween.toInt()) })
            } else {
                DataResult.Error(Exception("Error DataSource Room APi"))

            }
        } catch (e: Exception) {
            Log.e("ROOM ERROR:",e.message.toString())
            DataResult.Error(Exception("Error DataSource Room APi"))


        }

    }
}

fun RoomApi.toRoomHotel(days:Int): RoomHotel {
    return RoomHotel(this.id.toLong(), this.description, this.roomType, this.image?:"", this.capacity, this.price *days)
}

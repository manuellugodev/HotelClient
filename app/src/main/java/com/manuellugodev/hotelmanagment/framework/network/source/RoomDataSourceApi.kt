package com.manuellugodev.hotelmanagment.framework.network.source

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.core.domain.utils.convertDateToString
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomDataSource
import com.manuellugodev.hotelmanagment.framework.network.entities.RoomApi
import com.manuellugodev.hotelmanagment.framework.network.request.RoomRequest
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class RoomDataSourceApi(private val request: RoomRequest) : RoomDataSource {
    override suspend fun searchRooms(): DataResult<List<RoomHotel>> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchRooms(
        desiredStartTime: Date,
        desiredEndTime: Date,
        guests: Int
    ): DataResult<List<RoomHotel>> {

        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("GMT")

            val result = request.service.getAllRoomsAvailable(
                true,
                convertDateToString(desiredStartTime),
                convertDateToString(desiredEndTime),
                guests
            )

            if (result.isSuccessful) {

                val date1 = Instant.ofEpochMilli(desiredStartTime.time).atZone(ZoneId.systemDefault()).toLocalDate()
                val date2 = Instant.ofEpochMilli(desiredEndTime.time).atZone(ZoneId.systemDefault()).toLocalDate()

                val daysBetween = ChronoUnit.DAYS.between(date1, date2)

                DataResult.Success(result.body()?.data!!.map { it.toRoomHotel(daysBetween.toInt()) })
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

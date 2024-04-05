package com.manuellugodev.hotelmanagment.framework.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.manuellugodev.hotelmanagment.domain.model.Customer
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

@Entity
data class RoomHotelLocal(
    @PrimaryKey val id: Long,
    val description:String,
    val roomType:String,
    val pathImage:String,
    val peopleQuantity:Int,
    val price:Double

)

fun RoomHotel.toRoomHotelLocal():RoomHotelLocal{
    return RoomHotelLocal(id, description, roomType, pathImage, peopleQuantity, price)
}

fun RoomHotelLocal.toRoomHotelDomain():RoomHotel{
    return RoomHotel(id, description, roomType, pathImage, peopleQuantity, price)
}

class RoomHotelLocalConverter() {

    @TypeConverter
    fun fromJson(json:String):RoomHotelLocal{
        return Gson().fromJson(json,RoomHotelLocal::class.java)
    }

    @TypeConverter
    fun toJson(roomHotelLocal: RoomHotelLocal):String{
        return Gson().toJson(roomHotelLocal)
    }
}
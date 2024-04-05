package com.manuellugodev.hotelmanagment.framework.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.manuellugodev.hotelmanagment.domain.model.Reservation

@Entity
data class ReservationLocal(
    @PrimaryKey var id:Int,
    val client:CustomerLocal,
    val roomHotelLocal: RoomHotelLocal,
    val checkIn:Long,
    val checkOut:Long,
    val price:Double,
    val taxPrice:Double,
    val totalPrice:Double
)

fun Reservation.toReservationLocal():ReservationLocal{
    return ReservationLocal(id,client.toCustomerLocal(),roomHotel.toRoomHotelLocal(),checkIn,checkOut,price,taxPrice,totalPrice)
}

fun ReservationLocal.toReservationDomain():Reservation{
    return Reservation(id,client.toCustomerDomain(),roomHotelLocal.toRoomHotelDomain(),checkIn,checkOut,price,taxPrice,totalPrice)
}
class ReservationLocalConverter() {

    @TypeConverter
    fun fromJson(json:String):ReservationLocal{
        return Gson().fromJson(json,ReservationLocal::class.java)
    }

    @TypeConverter
    fun toJson(reservationLocal: ReservationLocal):String{
        return Gson().toJson(reservationLocal)
    }
}

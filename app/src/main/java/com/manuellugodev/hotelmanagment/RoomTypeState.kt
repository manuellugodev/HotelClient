package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

sealed class RoomTypeState(){
    class Success(val data:List<RoomHotel>):RoomTypeState()
    class Pending(val progress:Int):RoomTypeState()
    class Error(val message:String):RoomTypeState()
}

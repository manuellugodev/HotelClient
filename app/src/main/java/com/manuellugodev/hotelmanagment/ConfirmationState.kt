package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

sealed class ConfirmationState {
    class Success(val data:Reservation):ConfirmationState()
    class Pending(val progress:Int):ConfirmationState()
    class Error(val message:String):ConfirmationState()
}
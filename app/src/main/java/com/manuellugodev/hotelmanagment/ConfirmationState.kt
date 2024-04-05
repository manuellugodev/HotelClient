package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.domain.model.Reservation

sealed class ConfirmationState {
    class SavedReservation(val data:Reservation):ConfirmationState()

    class ShowData(val dataReservation:Reservation):ConfirmationState()
    class Pending(val progress:Int):ConfirmationState()
    class Error(val message:String):ConfirmationState()
}
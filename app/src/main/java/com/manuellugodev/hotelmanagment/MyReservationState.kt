package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.domain.model.Reservation

sealed class MyReservationState {
    class ShowReservation(val listReservation: List<Reservation>) : MyReservationState()
    class Failure(val e: Exception) : MyReservationState()
    object Loading : MyReservationState()
}
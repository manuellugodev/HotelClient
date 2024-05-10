package com.manuellugodev.hotelmanagment.features.reservations.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation

sealed class MyReservationState {
    class ShowReservation(val listReservation: List<Reservation>) : MyReservationState()
    class Failure(val e: Exception) : MyReservationState()
    object Loading : MyReservationState()
}
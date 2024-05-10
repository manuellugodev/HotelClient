package com.manuellugodev.hotelmanagment.features.reservations.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation

sealed class ConfirmationState {
    class SavedReservation(val data: Reservation) : ConfirmationState()

    class ShowData(val dataReservation: Reservation) : ConfirmationState()
    object Pending : ConfirmationState()
    class Error(val message: String) : ConfirmationState()
}
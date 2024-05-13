package com.manuellugodev.hotelmanagment.features.reservations.utils

sealed class ConfirmationEvent {

    object sendConfirmation:ConfirmationEvent()
    object getTemporalReservation:ConfirmationEvent()

}
package com.manuellugodev.hotelmanagment.features.reservations.utils

sealed class MyReservationEvent {

    object OnDismissError:MyReservationEvent()

}
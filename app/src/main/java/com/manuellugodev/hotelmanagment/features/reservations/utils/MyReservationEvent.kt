package com.manuellugodev.hotelmanagment.features.reservations.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation

sealed class MyReservationEvent {

    object OnDismissError:MyReservationEvent()

    object GetMyReservations:MyReservationEvent()

    object GetUpcomingReservations : MyReservationEvent()

    object GetPastReservations : MyReservationEvent()

    class IntentDeleteAppointment(val reservation: Reservation) : MyReservationEvent()

    object DismissDeleteAppointment : MyReservationEvent()

    object ConfirmDeleteAppointment : MyReservationEvent()


}
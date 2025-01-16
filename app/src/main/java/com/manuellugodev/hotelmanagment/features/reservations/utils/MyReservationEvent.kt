package com.manuellugodev.hotelmanagment.features.reservations.utils

sealed class MyReservationEvent {

    object OnDismissError:MyReservationEvent()

    object GetMyReservations:MyReservationEvent()

    object GetUpcomingReservations : MyReservationEvent()

    object GetPastReservations : MyReservationEvent()

    class IntentDeleteAppointment(val reservationId: Int) : MyReservationEvent()

    object DismissDeleteAppointment : MyReservationEvent()

    object ConfirmDeleteAppointment : MyReservationEvent()


}
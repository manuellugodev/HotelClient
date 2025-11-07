package com.manuellugodev.hotelmanagment.features.reservations.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
data class MyReservationState(
    val showReservation:List<Reservation> = emptyList(),
    val showLoader:Boolean = false,
    val showErrorMsg:String ="",
    val searchMyReservations: Boolean = false,
    val optionSelected: ReservationFilter = ReservationFilter.UPCOMING,
    val showConfirmDelete: Boolean = false,
    val reservationSelectedId: Reservation? = null
)
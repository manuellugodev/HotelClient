package com.manuellugodev.hotelmanagment.features.reservations.utils

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
data class ConfirmationState(
    val showReservation:Reservation?=null,
    val loading:Boolean = false,
    val showError:String = "",
    val reservationSaved:Boolean=false
)
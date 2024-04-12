package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository
import com.manuellugodev.hotelmanagment.utils.DataResult

class SendConfirmationReservation(private val repository: ReservationRepository) {

    suspend operator fun invoke(reservation:Reservation): DataResult<Reservation> {

       return repository.sendReservation(reservation)

    }
}
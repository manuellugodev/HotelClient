package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository
import com.manuellugodev.hotelmanagment.domain.utils.DataResult

class GetTemporalReservation(private val repository: ReservationRepository) {

    suspend operator fun invoke(id:Long): DataResult<Reservation> {
        return repository.getTemporalReservation(id)
    }
}
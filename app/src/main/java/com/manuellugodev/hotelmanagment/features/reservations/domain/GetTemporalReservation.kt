package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

class GetTemporalReservation(private val repository: ReservationRepository) {

    suspend operator fun invoke(id:Long): DataResult<Reservation> {
        return repository.getTemporalReservation(id)
    }
}
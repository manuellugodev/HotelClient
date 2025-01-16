package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository

class DeleteReservation(private val repository: ReservationRepository) {

    suspend operator fun invoke(id: Int): DataResult<String> {
        return repository.deleteReservation(id)
    }

}
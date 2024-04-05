package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.data.ReservationRepository
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class GetTemporalReservation(private val repository: ReservationRepository) {

    suspend operator fun invoke(id:Long): DataResult<Reservation> {
        return repository.getTemporalReservation(id)
    }
}
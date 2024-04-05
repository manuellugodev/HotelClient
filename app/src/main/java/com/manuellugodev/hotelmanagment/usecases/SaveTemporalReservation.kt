package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.data.ReservationRepository
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class SaveTemporalReservation(private val repository: ReservationRepository) {


    suspend operator fun invoke(reservation: Reservation) :DataResult<Reservation>{
        return repository.saveTemporalReservation(reservation)
    }
}
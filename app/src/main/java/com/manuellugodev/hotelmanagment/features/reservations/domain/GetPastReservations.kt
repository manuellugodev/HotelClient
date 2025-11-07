package com.manuellugodev.hotelmanagment.features.reservations.domain

import com.manuellugodev.hotelmanagment.features.core.domain.TimeProvider
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository

class GetPastReservations(
    private val repository: ReservationRepository,
    private val time: TimeProvider
) {

    suspend operator fun invoke(): DataResult<List<Reservation>> {

        return repository.getPastReservations(time.getNowTime())

    }
}
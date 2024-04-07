package com.manuellugodev.hotelmanagment.usecases

import com.manuellugodev.hotelmanagment.data.ReservationRepository
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class GetMyReservations(private val repository: ReservationRepository) {

    suspend operator fun invoke(guest: Int): DataResult<List<Reservation>> {
        return repository.getMyReservations(guest);
    }

}
package com.manuellugodev.hotelmanagment.features.reservations.domain


import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository
import com.manuellugodev.hotelmanagment.domain.utils.DataResult

class GetMyReservations(private val repository: ReservationRepository) {

    suspend operator fun invoke(guest: Int): DataResult<List<Reservation>> {
        return repository.getMyReservations(guest);
    }

}
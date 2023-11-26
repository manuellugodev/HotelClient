package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.domain.model.Reservation

class ReservationRepositoryImpl(private val dataSourceReservation: DataSourceReservation) :
    ReservationRepository {

    override suspend fun sendReservation(reservation: Reservation) = dataSourceReservation.saveReservation(reservation)
}
package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.network.entities.Appointment
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class ReservationRepositoryImpl(private val dataSourceReservation: DataSourceReservation) :
    ReservationRepository {

    override suspend fun sendReservation(reservation: Reservation) = dataSourceReservation.saveReservation(reservation)
    override suspend fun getReservations(): DataResult<List<Reservation>> {
       return dataSourceReservation.getReservation()
    }
}
package com.manuellugodev.hotelmanagment.features.reservations.data

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.domain.utils.DataResult

interface DataSourceReservation {

    suspend fun saveReservation(reservation: Reservation): DataResult<Reservation>
    suspend fun getReservation(): DataResult<List<Reservation>>
    suspend fun getMyReservations(guest: Int): DataResult<List<Reservation>>
}
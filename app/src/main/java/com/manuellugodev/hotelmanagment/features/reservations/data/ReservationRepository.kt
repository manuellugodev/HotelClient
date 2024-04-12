package com.manuellugodev.hotelmanagment.features.reservations.data

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.DataResult

interface ReservationRepository {

    suspend fun sendReservation(reservation: Reservation): DataResult<Reservation>

    suspend fun getReservations(): DataResult<List<Reservation>>

    suspend fun getTemporalReservation(id: Long): DataResult<Reservation>

    suspend fun saveTemporalReservation(reservation: Reservation): DataResult<Reservation>
    suspend fun getMyReservations(guest: Int): DataResult<List<Reservation>>
}
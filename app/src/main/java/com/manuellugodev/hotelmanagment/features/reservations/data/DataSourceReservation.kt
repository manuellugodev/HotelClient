package com.manuellugodev.hotelmanagment.features.reservations.data

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import java.util.Date

interface DataSourceReservation {

    suspend fun saveReservation(reservation: Reservation): DataResult<Reservation>
    suspend fun getReservation(): DataResult<List<Reservation>>
    suspend fun getMyReservations(guest: Int): DataResult<List<Reservation>>
    suspend fun getUpcomingReservations(guest: Int, date: Date): DataResult<List<Reservation>>
    suspend fun getPastReservations(guest: Int, date: Date): DataResult<List<Reservation>>
    suspend fun deleteReservation(id: Int): DataResult<String>


}
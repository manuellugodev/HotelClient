package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.data.DataSourceReservation
import java.util.Date

class FakeSourceAppointment:DataSourceReservation {
    override suspend fun saveReservation(reservation: Reservation): DataResult<Reservation> {
        TODO("Not yet implemented")
    }

    override suspend fun getReservation(): DataResult<List<Reservation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyReservations(guest: Int): DataResult<List<Reservation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingReservations(
        guest: Int,
        date: Date
    ): DataResult<List<Reservation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPastReservations(
        guest: Int,
        date: Date
    ): DataResult<List<Reservation>> {
        TODO("Not yet implemented")
    }
}
package com.manuellugodev.hotelmanagment.features.reservations.data

import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationLocal

class ReservationRepositoryImpl(
    private val dataSourceReservation: DataSourceReservation,
    private val dataSourceReservationLocal: DataSourceReservationLocal,
    private val tokenManagment: TokenManagment
) :
    ReservationRepository {

    override suspend fun sendReservation(reservation: Reservation) =
        dataSourceReservation.saveReservation(reservation)

    override suspend fun getReservations(): DataResult<List<Reservation>> {
        return dataSourceReservation.getReservation()
    }

    override suspend fun getTemporalReservation(id: Long): DataResult<Reservation> {

        return dataSourceReservationLocal.getTemporalReservation(id)
    }

    override suspend fun saveTemporalReservation(reservation: Reservation): Result<Reservation> {
        return dataSourceReservationLocal.saveReservationLocal(reservation)
    }

    override suspend fun getMyReservations(guestId: Int): DataResult<List<Reservation>> {
        val id = tokenManagment.getGuestId()
        return dataSourceReservation.getMyReservations(id)
    }
}
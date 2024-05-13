package com.manuellugodev.hotelmanagment.framework.roomdb

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.toReservationDomain
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.toReservationLocal
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import javax.inject.Inject

class DataSourceReservationRoomDB @Inject constructor(private val dao: ReservationDao) :
    DataSourceReservationLocal {
    override suspend fun getTemporalReservation(id: Long): DataResult<Reservation> {
        return try {
            val result = dao.findById(id)
            DataResult.Success(result.toReservationDomain())
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun saveReservationLocal(reservation: Reservation): Result<Reservation> {
        return try {
            dao.saveTemporalReservation(reservation.toReservationLocal())
            Result.success(reservation)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
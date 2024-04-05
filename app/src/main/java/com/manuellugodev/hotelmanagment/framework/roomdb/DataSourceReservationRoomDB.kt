package com.manuellugodev.hotelmanagment.framework.roomdb

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.ReservationLocal
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.toReservationDomain
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.toReservationLocal
import com.manuellugodev.hotelmanagment.utils.fakes.reservationMock
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
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

    override suspend fun saveReservationLocal(reservation: Reservation): DataResult<Reservation> {
        return try {
            dao.saveTemporalReservation(reservation.toReservationLocal())
            DataResult.Success(reservation)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

}
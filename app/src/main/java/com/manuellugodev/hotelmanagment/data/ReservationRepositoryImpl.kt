package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationLocal
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

class ReservationRepositoryImpl(private val dataSourceReservation: DataSourceReservation,private val dataSourceReservationLocal: DataSourceReservationLocal) :
    ReservationRepository {

    override suspend fun sendReservation(reservation: Reservation) = dataSourceReservation.saveReservation(reservation)
    override suspend fun getReservations(): DataResult<List<Reservation>> {
       return dataSourceReservation.getReservation()
    }

    override suspend fun getTemporalReservation(id:Long):DataResult<Reservation>{

        return dataSourceReservationLocal.getTemporalReservation(id)
    }

    override suspend fun saveTemporalReservation(reservation: Reservation): DataResult<Reservation> {
        return dataSourceReservationLocal.saveReservationLocal(reservation)
    }
}
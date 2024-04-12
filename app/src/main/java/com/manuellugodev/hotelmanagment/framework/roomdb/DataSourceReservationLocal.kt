package com.manuellugodev.hotelmanagment.framework.roomdb

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.DataResult

interface DataSourceReservationLocal {

    suspend fun getTemporalReservation(id: Long): DataResult<Reservation>
    suspend fun saveReservationLocal(reservation: Reservation): DataResult<Reservation>


}
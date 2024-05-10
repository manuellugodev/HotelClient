package com.manuellugodev.hotelmanagment.framework.roomdb

import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult

interface DataSourceReservationLocal {

    suspend fun getTemporalReservation(id: Long): DataResult<Reservation>
    suspend fun saveReservationLocal(reservation: Reservation): DataResult<Reservation>


}
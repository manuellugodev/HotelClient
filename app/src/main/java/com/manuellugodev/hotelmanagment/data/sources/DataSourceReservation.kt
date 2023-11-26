package com.manuellugodev.hotelmanagment.data.sources

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.vo.DataResult

interface DataSourceReservation {

    suspend fun saveReservation(reservation: Reservation): DataResult<Reservation>
}
package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.data.DataSourceReservation
import com.manuellugodev.hotelmanagment.framework.network.entities.Appointment
import com.manuellugodev.hotelmanagment.framework.network.entities.toCustomer
import com.manuellugodev.hotelmanagment.framework.network.request.AppointmentBody
import com.manuellugodev.hotelmanagment.framework.network.request.AppointmentRequest
import com.manuellugodev.hotelmanagment.domain.utils.DataResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DataSourceAppointmentApi(private val request: AppointmentRequest) : DataSourceReservation {
    override suspend fun saveReservation(reservation: Reservation): DataResult<Reservation> {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val checkIn = dateFormat.format(Date(reservation.checkIn))
            val checkOut = dateFormat.format(Date(reservation.checkOut));
            val bodyAppointment = AppointmentBody(
                reservation.client.id.toInt(), reservation.roomHotel.id.toInt(),
                checkIn, checkOut, reservation.price.toString()
            )

            val result = request.service.sendAppointment(
                bodyAppointment.guestId,
                bodyAppointment.roomId,
                bodyAppointment.startTime,
                bodyAppointment.endTime,
                bodyAppointment.purpose
            )

            if (result.isSuccessful) {
                return DataResult.Success(reservation)
            } else {
                DataResult.Error(Exception(result.errorBody()?.string()))
            }
        } catch (e: Exception) {

            DataResult.Error(e)
        }

    }

    override suspend fun getReservation(): DataResult<List<Reservation>> {
        try {


            val result = request.service.getAppointments()

            if (result.isSuccessful) {
                val reservations = result.body()

                if (!reservations.isNullOrEmpty()) {
                    return DataResult.Success(reservations.map { it.toReservation() })
                }

            } else {

                DataResult.Error(Exception("error"))

            }
        } catch (e: Exception) {

            DataResult.Error(Exception("error"))
        }

        return DataResult.Error(Exception("error"))

    }

    override suspend fun getMyReservations(guest: Int): DataResult<List<Reservation>> {
        return try {
            val result = request.service.getMyAppointments(guest)
            if (result.isSuccessful) {
                DataResult.Success(result.body()!!.map { it.toReservation() })
            } else {
                DataResult.Error(Exception("Error getting reservations"))
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}


fun Appointment.toReservation(): Reservation {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    try {
        val dateStart = dateFormat.parse(startTime)
        val dateEnd = dateFormat.parse(endTime)
        val guest = guest.toCustomer()
        val room = room.toRoomHotel()
        return Reservation(
            appointmentId,
            guest,
            room,
            dateStart.time,
            dateEnd.time,
            room.price,
            0.0,
            room.price * 1.10
        )
    } catch (e: Exception) {
        println("Error parsing date: ${e.message}")
    }

    throw java.lang.Exception()
}


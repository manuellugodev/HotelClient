package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.AppointmentsNotFound
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.core.domain.utils.convertDateToStringDefaultTimeZone
import com.manuellugodev.hotelmanagment.features.core.domain.utils.convertLongToDateTimeRoom
import com.manuellugodev.hotelmanagment.features.reservations.data.DataSourceReservation
import com.manuellugodev.hotelmanagment.framework.network.entities.Appointment
import com.manuellugodev.hotelmanagment.framework.network.entities.toCustomer
import com.manuellugodev.hotelmanagment.framework.network.request.AppointmentBody
import com.manuellugodev.hotelmanagment.framework.network.request.AppointmentRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DataSourceAppointmentApi(private val request: AppointmentRequest) : DataSourceReservation {
    override suspend fun saveReservation(reservation: Reservation): DataResult<Reservation> {
        return try {
            val checkIn = convertLongToDateTimeRoom(reservation.checkIn, pattern = "yyyy-MM-dd")
            val checkOut = convertLongToDateTimeRoom(reservation.checkOut, pattern = "yyyy-MM-dd")
            val bodyAppointment = AppointmentBody(
                reservation.client.id.toInt(), reservation.roomHotel.id.toInt(),
                checkIn, checkOut, "Travel", reservation.totalPrice
            )

            val result = request.service.sendAppointment(
                bodyAppointment.guestId,
                bodyAppointment.roomId,
                bodyAppointment.startTime,
                bodyAppointment.endTime,
                bodyAppointment.purpose,
                bodyAppointment.total
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
                val reservations = result.body()?.data

                if (!reservations.isNullOrEmpty()) {
                    return DataResult.Success(reservations.map { it.toReservationPreview() })
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
                DataResult.Success(result.body()?.data!!.map { it.toReservation() })
            } else {
                if (result.code() == 404) {
                    DataResult.Error(AppointmentsNotFound())
                } else {
                    DataResult.Error(Exception("Error getting reservations"))

                }
            }
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getUpcomingReservations(
        guest: Int,
        date: Date
    ): DataResult<List<Reservation>> {
        return try {
            val result = request.service.getUpcomingAppointments(
                guest,
                convertDateToStringDefaultTimeZone(date)
            )

            if (result.isSuccessful) {
                DataResult.Success(result.body()?.data!!.map { it.toReservation() })
            } else {
                if (result.code() == 404) {
                    DataResult.Error(AppointmentsNotFound())
                } else {
                    DataResult.Error(Exception("Error getting reservations"))
                }
            }

        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

}


fun Appointment.toReservationPreview(): Reservation {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    try {
        val dateStart = dateFormat.parse(startTime)
        val dateEnd = dateFormat.parse(endTime)
        val guest = guest.toCustomer()
        val room = room.toRoomHotel(1)
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

fun Appointment.toReservation(): Reservation {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    try {
        val dateStart = dateFormat.parse(startTime)
        val dateEnd = dateFormat.parse(endTime)
        val guest = guest.toCustomer()
        val room = room.toRoomHotel(1)
        return Reservation(
            appointmentId,
            guest,
            room,
            dateStart.time,
            dateEnd.time,
            room.price,
            0.0,
            total
        )
    } catch (e: Exception) {
        println("Error parsing date: ${e.message}")
    }

    throw java.lang.Exception()
}




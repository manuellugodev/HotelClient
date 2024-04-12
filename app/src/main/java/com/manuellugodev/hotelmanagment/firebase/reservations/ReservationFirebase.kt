package com.manuellugodev.hotelmanagment.firebase.reservations

import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.data.DataSourceReservation
import com.manuellugodev.hotelmanagment.utils.DataResult
import kotlinx.coroutines.tasks.await

class ReservationFirebase(private val database: FirebaseFirestore) : DataSourceReservation {

    override suspend fun saveReservation(reservation: Reservation): DataResult<Reservation> {
        return try {
            val data = database.collection("reservations")
                .add(reservation).await()

            val dataUpdate =
                database.collection("rooms").document(reservation.roomHotel.id.toString())
                    .update("available", false).await()

            reservation.id = data.id.toInt()
            return DataResult.Success(reservation)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }

    override suspend fun getReservation(): DataResult<List<Reservation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyReservations(guest: Int): DataResult<List<Reservation>> {
        TODO("Not yet implemented")
    }
}
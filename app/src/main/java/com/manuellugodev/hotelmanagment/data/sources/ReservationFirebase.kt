package com.manuellugodev.hotelmanagment.data.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import kotlinx.coroutines.tasks.await

class ReservationFirebase(private val database:FirebaseFirestore) :DataSourceReservation{

    override suspend fun saveReservation(reservation: Reservation):DataResult<Reservation>{
        return try {
            val data = database.collection("reservations")
                .add(reservation).await()
            return DataResult.Success(reservation)
        }catch (e:Exception){
            DataResult.Error(e)
        }
    }
}
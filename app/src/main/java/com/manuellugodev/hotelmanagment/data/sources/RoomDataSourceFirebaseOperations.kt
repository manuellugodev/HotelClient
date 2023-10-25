package com.manuellugodev.hotelmanagment.data.sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import kotlinx.coroutines.tasks.await



class RoomDataSourceFirebaseOperations(val database: FirebaseFirestore) : RoomDataSource {
    override suspend fun searchRooms(): DataResult<List<RoomHotel>> {

        return try {
            val data = database.collection("rooms")
                .get().await()

            val result =data.documents.map {it.toParseRoomHotelDomain()  }

            DataResult.Success(result)
        } catch (e: Exception) {

            DataResult.Error(e)
        }
    }

    fun DocumentSnapshot.toParseRoomHotelDomain() :RoomHotel{
        val title = this.data?.get("title") as String
        val beds = (this.data?.get("beds") as String).toInt()
        val guests = (this.data?.get("guests") as String).toInt()
        val image = this.data?.get("image") as String
        val price = (this.data?.get("price") as String).toDouble()

        return RoomHotel(title, beds, image, guests, price)
    }
}
package com.manuellugodev.hotelmanagment.data.sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import kotlinx.coroutines.tasks.await
import java.util.Date


class RoomDataSourceFirebaseOperations(val database: FirebaseFirestore) : RoomDataSource {
    override suspend fun searchRooms(): DataResult<List<RoomHotel>> {

        return try {
            val data = database.collection("rooms").where(Filter.equalTo("available",true))
                .get().await()

            val result =data.documents.map {it.toParseRoomHotelDomain()  }

            DataResult.Success(result)
        } catch (e: Exception) {

            DataResult.Error(e)
        }
    }

    override suspend fun searchRooms(
        desiredStartTime: Date,
        desiredEndTime: Date,
        guests:Int
    ): DataResult<List<RoomHotel>> {
        TODO("Not yet implemented")
    }

    fun DocumentSnapshot.toParseRoomHotelDomain() :RoomHotel{
        val id = (this.data?.get("id") as Long)
        val title = this.data?.get("title") as String
        val beds = (this.data?.get("beds") as String).toInt()
        val guests = (this.data?.get("guests") as String).toInt()
        val image = this.data?.get("image") as String
        val price = (this.data?.get("price") as String).toDouble()

        return RoomHotel(id,title, "suite", image, guests, price)
    }
}
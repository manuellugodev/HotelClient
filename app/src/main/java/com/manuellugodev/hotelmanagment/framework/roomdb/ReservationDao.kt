package com.manuellugodev.hotelmanagment.framework.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.ReservationLocal


@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTemporalReservation(reservation: ReservationLocal)


    @Query("SELECT * FROM ReservationLocal where id=:id")
    fun findById(id:Long):ReservationLocal



}
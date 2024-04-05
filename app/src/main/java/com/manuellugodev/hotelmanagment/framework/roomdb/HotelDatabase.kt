package com.manuellugodev.hotelmanagment.framework.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

import com.manuellugodev.hotelmanagment.framework.roomdb.entities.CustomerLocal
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.CustomerLocalConverter
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.ReservationLocal
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.ReservationLocalConverter
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.RoomHotelLocal
import com.manuellugodev.hotelmanagment.framework.roomdb.entities.RoomHotelLocalConverter

@Database(entities = [ReservationLocal::class,CustomerLocal::class,RoomHotelLocal::class], version = 1)
@TypeConverters(ReservationLocalConverter::class,CustomerLocalConverter::class,RoomHotelLocalConverter::class)
abstract class HotelDatabase :RoomDatabase(){
    abstract fun reservationDao(): ReservationDao
}
package com.manuellugodev.hotelmanagment.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.data.sources.FirebaseLogin
import com.manuellugodev.hotelmanagment.data.sources.LoginDataSource
import com.manuellugodev.hotelmanagment.data.sources.ReservationFirebase
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSourceFirebaseOperations
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationLocal
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationRoomDB
import com.manuellugodev.hotelmanagment.framework.roomdb.HotelDatabase
import com.manuellugodev.hotelmanagment.framework.roomdb.ReservationDao
import com.manuellugodev.hotelmanagment.network.request.AppointmentRequest
import com.manuellugodev.hotelmanagment.network.request.RoomRequest
import com.manuellugodev.hotelmanagment.network.source.DataSourceAppointmentApi
import com.manuellugodev.hotelmanagment.network.source.RoomDataSourceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun loginDataSource(auth: FirebaseAuth):LoginDataSource{
        return FirebaseLogin(auth)
    }

    @Provides
    fun roomDataSource(database:FirebaseFirestore):RoomDataSource{
        return RoomDataSourceFirebaseOperations(database)
    }

    @Provides
    fun provideDataSourceReservation(firestore: FirebaseFirestore): DataSourceReservation {
        return ReservationFirebase(firestore)
    }

    @Provides
    @Named("api")
    fun provideDataSourceReservationApi(request: AppointmentRequest): DataSourceReservation {
        return DataSourceAppointmentApi(request)
    }

    @Provides
    @Named("api")
    fun provideDataSourceRoomApi(request: RoomRequest): RoomDataSource {
        return RoomDataSourceApi(request)
    }
    @Provides
    fun provideDataSourceReservationLocal(daoReservation: ReservationDao): DataSourceReservationLocal {
        return DataSourceReservationRoomDB(daoReservation)
    }


    @Provides
    @Singleton
    fun provideDatabaseRoom(@ApplicationContext applicationContext:Context): HotelDatabase {
        val db = Room.databaseBuilder(
            applicationContext,
            HotelDatabase::class.java, "database-hotel"
        ).build()
        return db
    }

    @Provides
    @Singleton
    fun provideDaoReservation(db: HotelDatabase): ReservationDao {
        return db.reservationDao()
    }


}
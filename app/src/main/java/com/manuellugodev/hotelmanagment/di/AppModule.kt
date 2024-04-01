package com.manuellugodev.hotelmanagment.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.data.sources.FirebaseLogin
import com.manuellugodev.hotelmanagment.data.sources.LoginDataSource
import com.manuellugodev.hotelmanagment.data.sources.ReservationFirebase
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSourceFirebaseOperations
import com.manuellugodev.hotelmanagment.network.AppointmentRequest
import com.manuellugodev.hotelmanagment.network.source.DataSourceAppointmentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

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
}
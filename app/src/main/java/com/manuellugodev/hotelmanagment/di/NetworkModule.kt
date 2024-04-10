package com.manuellugodev.hotelmanagment.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.network.request.AppointmentRequest
import com.manuellugodev.hotelmanagment.network.request.RoomRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideFirebaseAuthentication(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseFirestoreDatabase():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideApointmentRequest(
        @Named("baseUrl") baseUrl: String,
        @Named("User") user: Map<String, String>
    ): AppointmentRequest {
        return AppointmentRequest(baseUrl, user["username"] ?: "", user["password"] ?: "")
    }

    @Provides
    fun provideRoomRequest(
        @Named("baseUrl") baseUrl: String,
        @Named("User") user: Map<String, String>
    ): RoomRequest {
        return RoomRequest(baseUrl, user["username"] ?: "", user["password"] ?: "")
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider() = "http://10.0.2.2:8080"

    @Provides
    @Singleton
    @Named("User")
    fun userDefault() =
        mapOf<String, String>(Pair("username", "Manuel"), Pair("password", "test123"))
}
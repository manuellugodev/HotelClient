package com.manuellugodev.hotelmanagment.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment
import com.manuellugodev.hotelmanagment.framework.network.request.AppointmentRequest
import com.manuellugodev.hotelmanagment.framework.network.request.LoginRequest
import com.manuellugodev.hotelmanagment.framework.network.request.ProfileRequest
import com.manuellugodev.hotelmanagment.framework.network.request.RoomRequest
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
    fun provideFirebaseFirestoreDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideApointmentRequest(
        @Named("baseUrl") baseUrl: String,
        token: TokenManagment
    ): AppointmentRequest {
        return AppointmentRequest(baseUrl, token)
    }

    @Provides
    fun provideRoomRequest(
        @Named("baseUrl") baseUrl: String,
        token: TokenManagment
    ): RoomRequest {
        return RoomRequest(baseUrl, token)
    }

    @Provides
    fun provideLoginRequest(@Named("baseUrl") baseUrl: String): LoginRequest {
        return LoginRequest(baseUrl)
    }

    @Provides
    fun provideProfileRequest(
        @Named("baseUrl") baseUrl: String,
        token: TokenManagment
    ): ProfileRequest {
        return ProfileRequest(baseUrl, token)
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider() =
        "https://app-hotel-5687665021bc.herokuapp.com/" // "http://10.0.2.2:8080/"


}

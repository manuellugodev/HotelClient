package com.manuellugodev.hotelmanagment.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.manuellugodev.hotelmanagment.network.TokenProvider
import com.manuellugodev.hotelmanagment.network.request.AppointmentRequest
import com.manuellugodev.hotelmanagment.network.request.LoginRequest
import com.manuellugodev.hotelmanagment.network.request.RoomRequest
import com.manuellugodev.hotelmanagment.utils.vo.TokenApi
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
        token: TokenProvider
    ): AppointmentRequest {
        return AppointmentRequest(baseUrl, token)
    }

    @Provides
    fun provideRoomRequest(
        @Named("baseUrl") baseUrl: String,
        token: TokenProvider
    ): RoomRequest {
        return RoomRequest(baseUrl, token)
    }

    @Provides
    fun provideLoginRequest(@Named("baseUrl") baseUrl: String): LoginRequest {
        return LoginRequest(baseUrl)
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


    @Provides
    @Singleton
    fun tokeProvider(): TokenProvider {
        return TokenApi("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW51ZWwiLCJpYXQiOjE3MTI3OTA2MzIsImV4cCI6MTcxMjc5NDIzMn0.83wuXA6LpZUVRToZiP83RjB4epqMYe5Xnd04alpPHCM")
    }
}

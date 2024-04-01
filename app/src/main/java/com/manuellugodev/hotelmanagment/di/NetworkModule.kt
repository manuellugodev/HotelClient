package com.manuellugodev.hotelmanagment.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manuellugodev.hotelmanagment.network.AppointmentRequest
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
    fun provideApiRequest(@Named("baseUrl") baseUrl: String):AppointmentRequest{
        return AppointmentRequest(baseUrl)
    }

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider() = "http://10.0.2.2:8080"
}
package com.manuellugodev.hotelmanagment.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.manuellugodev.hotelmanagment.data.sources.FirebaseLogin
import com.manuellugodev.hotelmanagment.data.sources.LoginDataSource
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSourceFirebaseOperations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}
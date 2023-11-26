package com.manuellugodev.hotelmanagment.di

import com.manuellugodev.hotelmanagment.data.LoginRepository
import com.manuellugodev.hotelmanagment.data.LoginRepositoryImpl
import com.manuellugodev.hotelmanagment.data.ReservationRepository
import com.manuellugodev.hotelmanagment.data.ReservationRepositoryImpl
import com.manuellugodev.hotelmanagment.data.RoomRepository
import com.manuellugodev.hotelmanagment.data.RoomRepositoryImpl
import com.manuellugodev.hotelmanagment.data.sources.DataSourceReservation
import com.manuellugodev.hotelmanagment.data.sources.LoginDataSource
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideLoginRepository(dataSource:LoginDataSource):LoginRepository{
        return LoginRepositoryImpl(dataSource)
    }

    @Provides
    fun provideRoomRepository(dataSource: RoomDataSource): RoomRepository {
        return RoomRepositoryImpl(dataSource)
    }

    @Provides
    fun provideReservationRepository(dataSource:DataSourceReservation): ReservationRepository {
        return ReservationRepositoryImpl(dataSource)
    }
}
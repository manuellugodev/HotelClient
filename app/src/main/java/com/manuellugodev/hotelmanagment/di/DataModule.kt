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
import com.manuellugodev.hotelmanagment.data.sources.TokenManagment
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideLoginRepository(
        @Named("api") dataSource: LoginDataSource,
        tokenManagment: TokenManagment
    ): LoginRepository {
        return LoginRepositoryImpl(dataSource, tokenManagment)
    }

    @Provides
    fun provideRoomRepository(@Named("api")dataSource: RoomDataSource): RoomRepository {
        return RoomRepositoryImpl(dataSource)
    }

    @Provides
    fun provideReservationRepository(@Named("api")dataSource:DataSourceReservation,dataSourceLocal: DataSourceReservationLocal): ReservationRepository {
        return ReservationRepositoryImpl(dataSource,dataSourceLocal)
    }
}
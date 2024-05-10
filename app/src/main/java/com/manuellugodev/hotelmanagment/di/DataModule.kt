package com.manuellugodev.hotelmanagment.di

import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository
import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepositoryImpl
import com.manuellugodev.hotelmanagment.features.profile.data.DataSourceProfile
import com.manuellugodev.hotelmanagment.features.profile.data.ProfileRepository
import com.manuellugodev.hotelmanagment.features.profile.data.ProfileRepositoryImpl
import com.manuellugodev.hotelmanagment.features.reservations.data.DataSourceReservation
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepositoryImpl
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomDataSource
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomRepository
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomRepositoryImpl
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationLocal
import com.manuellugodev.hotelmanagment.domain.TokenManagment
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
    fun provideRoomRepository(@Named("api") dataSource: RoomDataSource): RoomRepository {
        return RoomRepositoryImpl(dataSource)
    }

    @Provides
    fun provideProfileRepository(
        profileSource: DataSourceProfile,
        tokenManagment: TokenManagment
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileSource, tokenManagment)
    }

    @Provides
    fun provideReservationRepository(
        @Named("api") dataSource: DataSourceReservation,
        dataSourceLocal: DataSourceReservationLocal
    ): ReservationRepository {
        return ReservationRepositoryImpl(dataSource, dataSourceLocal)
    }
}
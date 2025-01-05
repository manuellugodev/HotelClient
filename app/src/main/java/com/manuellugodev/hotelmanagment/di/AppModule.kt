package com.manuellugodev.hotelmanagment.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.core.domain.TimeProvider
import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment
import com.manuellugodev.hotelmanagment.features.profile.data.DataSourceProfile
import com.manuellugodev.hotelmanagment.features.reservations.data.DataSourceReservation
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomDataSource
import com.manuellugodev.hotelmanagment.framework.TimeAndroid
import com.manuellugodev.hotelmanagment.framework.network.request.AppointmentRequest
import com.manuellugodev.hotelmanagment.framework.network.request.LoginRequest
import com.manuellugodev.hotelmanagment.framework.network.request.ProfileRequest
import com.manuellugodev.hotelmanagment.framework.network.request.RoomRequest
import com.manuellugodev.hotelmanagment.framework.network.source.DataSourceAppointmentApi
import com.manuellugodev.hotelmanagment.framework.network.source.DataSourceProfileApi
import com.manuellugodev.hotelmanagment.framework.network.source.LoginDataSourceApi
import com.manuellugodev.hotelmanagment.framework.network.source.RoomDataSourceApi
import com.manuellugodev.hotelmanagment.framework.network.source.TokenManagmentImpl
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationLocal
import com.manuellugodev.hotelmanagment.framework.roomdb.DataSourceReservationRoomDB
import com.manuellugodev.hotelmanagment.framework.roomdb.HotelDatabase
import com.manuellugodev.hotelmanagment.framework.roomdb.ReservationDao
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
    fun provideDatabaseRoom(@ApplicationContext applicationContext: Context): HotelDatabase {
        return HotelDatabase.create(applicationContext)
    }

    @Provides
    @Singleton
    fun provideDaoReservation(db: HotelDatabase): ReservationDao {
        return db.reservationDao()
    }

    @Provides
    @Named("api")
    fun provideLoginDataSourceApi(request: LoginRequest): LoginDataSource {
        return LoginDataSourceApi(request)
    }

    @Provides
    fun provideProfileDataSource(request: ProfileRequest): DataSourceProfile {
        return DataSourceProfileApi(request)
    }

    @Provides
    fun provideTokenManagment(sharedPreferences: SharedPreferences): TokenManagment {
        return TokenManagmentImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): SharedPreferences {
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "my_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return sharedPreferences
    }

    @Provides
    @Singleton
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey {
        return MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    @Provides
    @Singleton
    fun provideTimeAndroid(): TimeProvider {
        return TimeAndroid()
    }


}
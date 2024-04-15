package com.manuellugodev.hotelmanagment.di

import com.manuellugodev.hotelmanagment.features.auth.data.LoginRepository
import com.manuellugodev.hotelmanagment.features.auth.domain.CheckUserIsLogged
import com.manuellugodev.hotelmanagment.features.auth.domain.LoginWithUsernameAndPassword
import com.manuellugodev.hotelmanagment.features.profile.data.ProfileRepository
import com.manuellugodev.hotelmanagment.features.profile.usecase.DoLogOutSession
import com.manuellugodev.hotelmanagment.features.profile.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.features.reservations.data.ReservationRepository
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetMyReservations
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetReservations
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetTemporalReservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.SaveTemporalReservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.SendConfirmationReservation
import com.manuellugodev.hotelmanagment.features.rooms.data.RoomRepository
import com.manuellugodev.hotelmanagment.features.rooms.domain.SearchRoomAvailables

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideLoginWithUsernameAndPasswordUseCase(repository: LoginRepository): LoginWithUsernameAndPassword {
        return LoginWithUsernameAndPassword(repository)
    }

    @Provides
    fun provideSearchRoomAvailablesUseCase(repository: RoomRepository): SearchRoomAvailables {
        return SearchRoomAvailables(repository)
    }

    @Provides
    fun provideSendConfirmationReservationUsecase(repository: ReservationRepository): SendConfirmationReservation {
        return SendConfirmationReservation(repository)
    }

    @Provides
    fun provideGetReservationsUsecase(repository: ReservationRepository): GetReservations {
        return GetReservations(repository)
    }

    @Provides
    fun provideGetTemporalReservationUseCase(repository: ReservationRepository): GetTemporalReservation {
        return GetTemporalReservation(repository)
    }

    @Provides
    fun provideSaveTemporalReservationUseCase(repository: ReservationRepository): SaveTemporalReservation {
        return SaveTemporalReservation(repository)
    }

    @Provides
    fun provideGetMyResertavionsUseCase(repository: ReservationRepository): GetMyReservations {
        return GetMyReservations(repository);
    }

    @Provides
    fun provideCheckUserIsLoggedUseCase(repository: LoginRepository): CheckUserIsLogged {
        return CheckUserIsLogged(repository)
    }

    @Provides
    fun provideGetDataProfileUseCase(profileRepository: ProfileRepository): GetDataProfile {
        return GetDataProfile(profileRepository)
    }

    @Provides
    fun provideDoLogOutUseCase(loginRepository: LoginRepository): DoLogOutSession {
        return DoLogOutSession(loginRepository)
    }
}
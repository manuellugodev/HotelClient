package com.manuellugodev.hotelmanagment.di

import com.manuellugodev.hotelmanagment.data.LoginRepository
import com.manuellugodev.hotelmanagment.data.ReservationRepository
import com.manuellugodev.hotelmanagment.data.RoomRepository
import com.manuellugodev.hotelmanagment.usecases.CheckUserIsLogged
import com.manuellugodev.hotelmanagment.usecases.GetMyReservations
import com.manuellugodev.hotelmanagment.usecases.GetReservations
import com.manuellugodev.hotelmanagment.usecases.GetTemporalReservation
import com.manuellugodev.hotelmanagment.usecases.LoginWithUsernameAndPassword
import com.manuellugodev.hotelmanagment.usecases.SaveTemporalReservation
import com.manuellugodev.hotelmanagment.usecases.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.usecases.SendConfirmationReservation
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
    fun provideSendConfirmationReservationUsecase(repository: ReservationRepository):SendConfirmationReservation{
        return SendConfirmationReservation(repository)
    }

    @Provides
    fun provideGetReservationsUsecase(repository: ReservationRepository):GetReservations{
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
}
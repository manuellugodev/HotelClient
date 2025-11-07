package com.manuellugodev.hotelmanagment.di

import com.manuellugodev.hotelmanagment.features.core.domain.DispatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    fun provideManagerDispatchers(): DispatcherProvider =
        StandardDispatchers()
}
package com.manuellugodev.hotelmanagment.di

import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.StandarDistpatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DistpatchersModule {

    @Provides
    fun provideManagerDistpatchers():DistpatcherProvider =
        StandarDistpatchers()
}
package com.manuellugodev.hotelmanagment.features.core.domain

import kotlinx.coroutines.CoroutineDispatcher

interface DistpatcherProvider {

    val main:CoroutineDispatcher
    val default:CoroutineDispatcher
    val io:CoroutineDispatcher
    val uncofined:CoroutineDispatcher
}
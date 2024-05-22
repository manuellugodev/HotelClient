package com.manuellugodev.hotelmanagment.features.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class StandarDistpatchers:DistpatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val uncofined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}
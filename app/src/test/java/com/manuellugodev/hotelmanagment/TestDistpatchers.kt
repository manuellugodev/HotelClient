package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class TestDistpatchers(private val testDispatcher: TestDispatcher= StandardTestDispatcher()):DistpatcherProvider {
    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val uncofined: CoroutineDispatcher
        get() = testDispatcher
}
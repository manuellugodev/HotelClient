package com.manuellugodev.hotelmanagment

import com.manuellugodev.hotelmanagment.features.core.domain.StandarDistpatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineExtension(val  testDistpatchers:CoroutineDispatcher= StandardTestDispatcher()): TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDistpatchers)
    }


    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
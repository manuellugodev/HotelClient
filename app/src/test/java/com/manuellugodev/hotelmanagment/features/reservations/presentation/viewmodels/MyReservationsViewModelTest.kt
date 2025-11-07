package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import com.manuellugodev.hotelmanagment.MainCoroutineExtension
import com.manuellugodev.hotelmanagment.TestDispatchers
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.domain.DeleteReservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetMyReservations
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetPastReservations
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetUpcomingReservations
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import com.manuellugodev.hotelmanagment.utils.fakeReservation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MyReservationsViewModelTest{

    private val standardTest = StandardTestDispatcher()
    private val getMyReservations:GetMyReservations = mockk(relaxed = true)
    private val getUpcomingReservations: GetUpcomingReservations = mockk(relaxed = true)
    private val getPastReservations: GetPastReservations = mockk(relaxed = true)
    private val deleteReservation: DeleteReservation = mockk(relaxed = true)
    private lateinit var viewmodel:MyReservationsViewModel
    @get:Rule
    val rule= MainCoroutineExtension(standardTest)

    @Before
    fun setUp(){
        viewmodel = MyReservationsViewModel(
            getMyReservations,
            getUpcomingReservations,
            getPastReservations,
            deleteReservation,
            TestDispatchers(standardTest)
        )
    }
    @Test
    fun `WHEN event GetMyReservation THEN call GetMyReservationUseCase`()=runTest{
        viewmodel.onEvent(MyReservationEvent.GetMyReservations)

        advanceUntilIdle()
        coVerify { getMyReservations.invoke(any()) }
    }

    @Test
    fun `WHEN event GetMyReservation and GetMyReservationUseCase is success THEN UPDATE state with reservations`()=runTest{
        coEvery { getMyReservations.invoke(any()) } returns DataResult.Success(listOf(
            fakeReservation(), fakeReservation(), fakeReservation(), fakeReservation()
        ))
        viewmodel.onEvent(MyReservationEvent.GetMyReservations)

        advanceUntilIdle()
        assert(viewmodel.stateMyReservation.value.showReservation.isNotEmpty())
        assert(viewmodel.stateMyReservation.value.showErrorMsg.isEmpty())
    }
    @Test
    fun `WHEN event GetMyReservation and GetMyReservationUseCase is Error THEN UPDATE state with Error`()=runTest{
        val exception=Exception()
        coEvery { getMyReservations.invoke(any()) } returns DataResult.Error(exception)

        viewmodel.onEvent(MyReservationEvent.GetMyReservations)

        advanceUntilIdle()
        assert(viewmodel.stateMyReservation.value.showReservation.isEmpty())
        assert(viewmodel.stateMyReservation.value.showErrorMsg.isNotEmpty())
    }

    @Test
    fun `WHEN event OnDismissError THEN clean state ERROR`()= runTest {
        val exception=Exception()
        coEvery { getMyReservations.invoke(any()) } returns DataResult.Error(exception)

        viewmodel.onEvent(MyReservationEvent.GetMyReservations)

        advanceUntilIdle()

        assert(viewmodel.stateMyReservation.value.showErrorMsg.isNotEmpty())

        viewmodel.onEvent(MyReservationEvent.OnDismissError)

        advanceUntilIdle()

        assert(viewmodel.stateMyReservation.value.showErrorMsg.isEmpty())
    }




}
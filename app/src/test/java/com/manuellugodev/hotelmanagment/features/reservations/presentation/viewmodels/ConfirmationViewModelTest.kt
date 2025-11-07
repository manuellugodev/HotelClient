package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.manuellugodev.hotelmanagment.MainCoroutineExtension
import com.manuellugodev.hotelmanagment.TestDispatchers
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetTemporalReservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.SendConfirmationReservation
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.RESERVATION
import com.manuellugodev.hotelmanagment.features.reservations.utils.ConfirmationEvent
import com.manuellugodev.hotelmanagment.utils.fakeReservation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConfirmationViewModelTest{

    private val standarTest = StandardTestDispatcher()
    private val sendConfirmation:SendConfirmationReservation = mockk(relaxed = true)
    private val getTemporalReservation:GetTemporalReservation = mockk(relaxed = true)
    private val savedStateHandle:SavedStateHandle = mockk()

    private lateinit var viewModel:ConfirmationViewModel

    @get:Rule
    val rule= MainCoroutineExtension(standarTest)

    private val reservationIdMockk=2L
    @Before
    fun setUp(){
        every { savedStateHandle.get<Long>(RESERVATION) } returns reservationIdMockk
        viewModel= ConfirmationViewModel(sendConfirmation,getTemporalReservation,savedStateHandle,TestDispatchers(standarTest))
    }

    @Test
    fun `WHEN event getTemporalReservation THEN call getTemporalUsecase with Id`()= runTest {
        viewModel.onEvent(ConfirmationEvent.getTemporalReservation)

        advanceUntilIdle()

        coVerify { getTemporalReservation.invoke(reservationIdMockk) }
    }
    @Test
    fun `WHEN event getTemporalReservation and usecase is success THEN update State with showReservation`()= runTest {
        val reservation= fakeReservation()
        coEvery { getTemporalReservation.invoke(any()) } returns DataResult.Success(reservation)

        viewModel.onEvent(ConfirmationEvent.getTemporalReservation)
        advanceUntilIdle()

        assert(viewModel.confirmationState.value.showReservation==reservation)

    }
    @Test
    fun `WHEN event getTemporalReservation and usecase is failure THEN update State with error`()= runTest {
        val exception= Exception()
        coEvery { getTemporalReservation.invoke(any()) } returns DataResult.Error(exception)

        viewModel.onEvent(ConfirmationEvent.getTemporalReservation)
        advanceUntilIdle()

        assert(viewModel.confirmationState.value.showError.isNotEmpty())

    }

    @Test
    fun`WHEN event sendConfirmation THEN call SendConfirmationUseCase`()= runTest {
        val reservation= fakeReservation()
        updateStateWithReservation(reservation)

        viewModel.onEvent(ConfirmationEvent.sendConfirmation)
        advanceUntilIdle()
        coVerify { sendConfirmation.invoke(fakeReservation()) }
    }

    @Test
    fun`WHEN event sendConfirmation and SendConfirmation is success then update state reservationSaved`()= runTest {
        val reservation= fakeReservation()
        coEvery { sendConfirmation.invoke(fakeReservation()) } returns DataResult.Success(reservation)
        updateStateWithReservation(reservation)

        viewModel.onEvent(ConfirmationEvent.sendConfirmation)
        advanceUntilIdle()
        assert(viewModel.confirmationState.value.reservationSaved)
    }
    @Test
    fun`WHEN event sendConfirmation and SendConfirmation is error then update state with Error`()= runTest {
        val reservation= fakeReservation()
        coEvery { sendConfirmation.invoke(fakeReservation()) } returns DataResult.Error(
            Exception()
        )
        updateStateWithReservation(reservation)

        viewModel.onEvent(ConfirmationEvent.sendConfirmation)
        advanceUntilIdle()
        assert(viewModel.confirmationState.value.reservationSaved.not())
        assert(viewModel.confirmationState.value.showError.isNotEmpty())
    }

    @Test
    fun`WHEN event sendConfirmation and sendConfirmation is called with null reservation then UPDATE state with Error`()= runTest {

        viewModel.onEvent(ConfirmationEvent.sendConfirmation)
        advanceUntilIdle()
        assert(viewModel.confirmationState.value.reservationSaved.not())
        assert(viewModel.confirmationState.value.showError.isNotEmpty())
    }

    private fun updateStateWithReservation(reservation:Reservation) {
        coEvery { getTemporalReservation.invoke(any()) } returns DataResult.Success(reservation)
        viewModel.onEvent(ConfirmationEvent.getTemporalReservation)
    }


}
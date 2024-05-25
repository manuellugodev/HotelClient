package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import com.manuellugodev.hotelmanagment.MainCoroutineExtension
import com.manuellugodev.hotelmanagment.features.reservations.utils.ReservationEvent
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReservationViewModelTest{

    private val standardTest= StandardTestDispatcher()
    private lateinit var viewModel:ReservationViewModel

    @get:Rule
    val rule= MainCoroutineExtension(standardTest)

    @Before
    fun setUp(){

        viewModel= ReservationViewModel()
    }

    @Test
    fun `onVisible event is false THEN UPDATE STATE showDatePicker in false`(){

        viewModel.onEvent(ReservationEvent.OnVisibleDatePicker(visible = false))

        assertEquals(false,viewModel.stateReservation.value.showDatePicker)
    }

    @Test
    fun `OnVisible event is true THEN UPDATE STATE ShowDatePicker in true`(){
        viewModel.onEvent(ReservationEvent.OnVisibleDatePicker(visible = true))

        assertEquals(true,viewModel.stateReservation.value.showDatePicker)
    }

    @Test
    fun `OnVisibleGuestComposable event is true THEN UPDATE STATE showGuesSelector in true`(){
        viewModel.onEvent(ReservationEvent.OnVisibleGuestComposable(visible = true))

        assertEquals(true,viewModel.stateReservation.value.showGuestSelector)
    }

    @Test
    fun `OnVisibleGuestComposable event is false THEN UPDATE STATE showGuesSelector in false`(){
        viewModel.onEvent(ReservationEvent.OnVisibleGuestComposable(visible = false))

        assertEquals(false,viewModel.stateReservation.value.showGuestSelector)
    }

    @Test
    fun`ShowError event is called Then Update state showError with messageError `(){
        val messageError="DATE invalid"
        viewModel.onEvent(ReservationEvent.ShowError(messageError))

        assertEquals(messageError,viewModel.stateReservation.value.showError)
    }

    @Test
    fun`DismissError event is called Then Clean state ShowError to empty `(){
        val messageError="DATE invalid"
        viewModel.onEvent(ReservationEvent.ShowError(messageError))

        viewModel.onEvent(ReservationEvent.DismissError)
        assert(viewModel.stateReservation.value.showError.isEmpty())
    }

    @Test
    fun`NavigateToSearchRoom Event is called THEN UPDATE state navigateToSearchRoom `(){
        val startTime=1000L
        val endTime =2000L
        val guest = 2L

        viewModel.onEvent(ReservationEvent.NavigateToSearchRooms(startTime = startTime,endTime =endTime, guests = guest))

        val state=viewModel.stateReservation.value
        assert(state.navigateToSearchRooms!=null)

        assertEquals(startTime,state.navigateToSearchRooms?.startTime)
        assertEquals(endTime,state.navigateToSearchRooms?.endTime)
        assertEquals(guest,state.navigateToSearchRooms?.guests)
    }

    @Test
    fun`UpdateAdultCount event is called THEN UPDATE state numberGuestAdults`(){
        val adultsCount=2
        viewModel.onEvent(ReservationEvent.UpdateAdultsCount(adultsCount))

        assertEquals(adultsCount,viewModel.stateReservation.value.numberGuestAdults)

    }
    @Test
    fun`UpdateChildrenCount event is called THEN UPDATE state numberGuestChildren`(){
        val childrenCount=1
        viewModel.onEvent(ReservationEvent.UpdateChildrenCount(childrenCount))

        assertEquals(childrenCount,viewModel.stateReservation.value.numberGuestChildren)
    }

    @Test
    fun`CleanNavigation event is called THEN UPDATE state navigateToSearchRoom to null`(){
        viewModel.onEvent(ReservationEvent.CleanNavigation)

        assertEquals(null,viewModel.stateReservation.value.navigateToSearchRooms)
    }




}
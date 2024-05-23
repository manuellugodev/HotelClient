package com.manuellugodev.hotelmanagment.features.rooms.presentation

import androidx.lifecycle.SavedStateHandle
import com.manuellugodev.hotelmanagment.MainCoroutineExtension
import com.manuellugodev.hotelmanagment.TestDistpatchers
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.profile.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.features.reservations.domain.SaveTemporalReservation
import com.manuellugodev.hotelmanagment.features.rooms.domain.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeEvent
import com.manuellugodev.hotelmanagment.utils.fakeListHotel
import com.manuellugodev.hotelmanagment.utils.fakeProfile
import com.manuellugodev.hotelmanagment.utils.fakeReservation
import com.manuellugodev.hotelmanagment.utils.fakeRoomHotel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.Exception

@OptIn(ExperimentalCoroutinesApi::class)

class RoomTypeViewModelTest{

    private lateinit var viewModel:RoomTypeViewModel
    private val searchRoomAvailables:SearchRoomAvailables = mockk(relaxed = true)
    private val saveTemporalReservation:SaveTemporalReservation = mockk(relaxed = true)
    private val getDataProfile :GetDataProfile = mockk(relaxed = true)
    private val savedStateHandle:SavedStateHandle = mockk(relaxed = true)

    private val standarTest= StandardTestDispatcher()

    @get:Rule
    val rule=MainCoroutineExtension(standarTest)

    @Before
    fun setUp(){
        mocckInitValues(1000L,2000L,2L)
        viewModel= spyk(buildViewModel())
    }
    @Test
    fun `When init Viewmodel variable have right values`(){
        assert(viewModel.guests==2L)
        assert(viewModel.desiredStartTime==1000L)
        assert(viewModel.desiredEndTime==2000L)

    }


    @Test
    fun `WHEN onEvent is SearchRooms then called searchRoomsUseCase`()= runTest{
        viewModel.onEvent(RoomTypeEvent.SearchRooms)
        advanceUntilIdle()
        coVerify {
            searchRoomAvailables.invoke(any(),any(),any())
        }
    }


    @Test
    fun `WHEN event SearchRooms is called and searchRoomUseCase is successful THEN updated state`()= runTest{

        coEvery { searchRoomAvailables.invoke(any(),any(),2) } returns DataResult.Success(listOf(
            RoomHotel(1,"","","",2,200.0)
        ))

        viewModel.onEvent(RoomTypeEvent.SearchRooms)
        advanceUntilIdle()

        assert(viewModel.statusRoom.value.showRooms.isNotEmpty())
        assert(viewModel.statusRoom.value.searchRooms.not())

    }


    @Test
    fun `WHEN envent SearchRooms is called and SearchRoomsUseCase is failure Then update state`()= runTest{


       coEvery { searchRoomAvailables.invoke(any(),any(),any()) } returns DataResult.Error(Exception())

        assert(viewModel.statusRoom.value.searchRooms)

        viewModel.onEvent(RoomTypeEvent.SearchRooms)
        advanceUntilIdle()

        assert(viewModel.statusRoom.value.showRooms.isEmpty())
        assert(viewModel.statusRoom.value.searchRooms.not())
        assert(viewModel.statusRoom.value.showError.isNotEmpty())

    }
    @Test
    fun `WHEN event DismissError then Update State `()= runTest{
        coEvery { searchRoomAvailables.invoke(any(),any(),any()) } returns DataResult.Error(Exception())
        viewModel.onEvent(RoomTypeEvent.SearchRooms)
        advanceUntilIdle()
        assert(viewModel.statusRoom.value.showError.isNotEmpty())

        viewModel.onEvent(RoomTypeEvent.DismissError)
        assert(viewModel.statusRoom.value.showError.isEmpty())
    }

    @Test
    fun `WHEN event OnClickRoomSelected Then GetProfile`()= runTest{

        viewModel.onEvent(RoomTypeEvent.OnClickRoomSelected(fakeRoomHotel()))
        advanceUntilIdle()

        coVerify { getDataProfile.invoke() }

    }
    @Test
    fun `WHEN event OnClickRoomSelected and GetDataProfile is succesful THEN call saveReservationUsecase`()= runTest{

        viewModel.onEvent(RoomTypeEvent.OnClickRoomSelected(fakeRoomHotel()))

        coEvery { getDataProfile.invoke() } returns Result.success(fakeProfile())

        advanceUntilIdle()

        coVerify { saveTemporalReservation.invoke(any()) }
    }
    @Test
    fun `WHEN event OnClickRoomSelected and GetDataProfile is Failure THEN update state with error`()= runTest{

            viewModel.onEvent(RoomTypeEvent.OnClickRoomSelected(fakeRoomHotel()))
            val expception=Exception()
            coEvery { getDataProfile.invoke() } returns Result.failure(expception)

            advanceUntilIdle()

            assert(viewModel.statusRoom.value.showError.isNotEmpty())
        }

    @Test
    fun`WHEN Event OnClickRoomSelected and GetDataProfile is ok but SavedReservation is failure Then Update state with Error`()=runTest{

        coEvery { getDataProfile.invoke() } returns Result.success(fakeProfile())

        val exception=Exception()
        coEvery { saveTemporalReservation.invoke(any()) } returns  Result.failure(exception)

        viewModel.onEvent(RoomTypeEvent.OnClickRoomSelected(fakeRoomHotel()))

        advanceUntilIdle()

        assert(viewModel.statusRoom.value.showError.isNotEmpty())
    }
    @Test
    fun`WHEN Event OnClickRoomSelected and GetDataProfile && SavedReservation is ok Then Update state navigationToBookId`()=runTest{

        coEvery { getDataProfile.invoke() } returns Result.success(fakeProfile())

        coEvery { saveTemporalReservation.invoke(any()) } returns  Result.success(fakeReservation())

        viewModel.onEvent(RoomTypeEvent.OnClickRoomSelected(fakeRoomHotel()))

        advanceUntilIdle()

        assert(viewModel.statusRoom.value.navigateToBookId== fakeReservation().id.toLong())
    }


    private fun mocckInitValues(startTime: Long,endTime:Long,guests:Long){
        every { savedStateHandle.get<Long>(START_TIME) } returns startTime
        every { savedStateHandle.get<Long>(END_TIME) } returns endTime
        every { savedStateHandle.get<Long>(GUESTS) } returns guests

    }
    private fun buildViewModel():RoomTypeViewModel{
        return  RoomTypeViewModel(searchRoomAvailables,saveTemporalReservation,getDataProfile, savedStateHandle,TestDistpatchers(standarTest))
    }


}
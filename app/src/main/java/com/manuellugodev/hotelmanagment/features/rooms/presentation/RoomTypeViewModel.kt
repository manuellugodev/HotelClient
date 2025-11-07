package com.manuellugodev.hotelmanagment.features.rooms.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.DispatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.model.Customer
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.profile.domain.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.features.reservations.domain.SaveTemporalReservation
import com.manuellugodev.hotelmanagment.features.rooms.domain.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeEvent
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RoomTypeViewModel @Inject constructor(
    private val usecase: SearchRoomAvailables,
    private val useCaseSaveTemporalReservation: SaveTemporalReservation,
    private val useCaseGetMyProfileData: GetDataProfile,
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider

) : ViewModel() {

    val desiredStartTime: Long = savedStateHandle.get(START_TIME)?:0L
    val desiredEndTime: Long = savedStateHandle.get(END_TIME)?:0L
    val guests: Long = savedStateHandle.get(GUESTS)?:0L


    private val _statusRoom: MutableStateFlow<RoomTypeState> = MutableStateFlow(RoomTypeState(searchRooms = true))
    val statusRoom:StateFlow<RoomTypeState> = _statusRoom

    private fun searchRoomsAvailables(desiredStartTime: Long, desiredEndTime: Long, guests: Int) {

        _statusRoom.value=_statusRoom.value.copy(showLoader = true)
        viewModelScope.launch(dispatcher.io) {
            try {
                var result = usecase(Date(desiredStartTime), Date(desiredEndTime), guests)

                withContext(dispatcher.main){
                    when (result) {
                        is DataResult.Success -> {
                            _statusRoom.value = _statusRoom.value.copy(showRooms = result.data, showLoader = false)
                        }

                        is DataResult.Error -> {
                            _statusRoom.value = _statusRoom.value.copy(showError = result.exception.message.toString(), showLoader = false)
                        }
                    }
                }

            } catch (e: Exception) {
                withContext(dispatcher.main){
                    _statusRoom.value = _statusRoom.value.copy(showError = e.message.toString(), showLoader = false)

                }

            }
        }
    }

    private fun saveReservation(
        desiredStartTime: Long,
        desiredEndTime: Long,
        guests: Int,
        roomHotel: RoomHotel
    ) {

        viewModelScope.launch(dispatcher.io) {
            try {
                val customerResult= useCaseGetMyProfileData()
                if(customerResult.isSuccess){
                    val profile=customerResult.getOrThrow()
                    val customer = Customer(
                        profile.guestId.toLong(),
                        profile.firstName,
                        profile.lastName,
                        profile.email,
                        profile.phone
                    )
                    val reservationToSave = Reservation(1,customer,roomHotel,desiredStartTime,desiredEndTime,roomHotel.price,0.0,roomHotel.price)

                    val result = useCaseSaveTemporalReservation(reservationToSave)

                    if (result.isSuccess){
                        val reservationSaved=result.getOrThrow()
                        _statusRoom.value=_statusRoom.value.copy(navigateToBookId = reservationSaved.id.toLong())
                    }else{
                        _statusRoom.value = _statusRoom.value.copy(showError = "Reservation not saved, try again")
                    }
                }else{
                    _statusRoom.value = _statusRoom.value.copy(showError = "Something went wrong with user data")
                }
            } catch (e: Exception) {
                _statusRoom.value = _statusRoom.value.copy(showError = "Something went wrong, try again")

            }
        }

    }

    fun onEvent(event: RoomTypeEvent){

        when(event){
            RoomTypeEvent.DismissError -> {
                _statusRoom.value = _statusRoom.value.copy(showError = "")
            }
            is RoomTypeEvent.OnClickRoomSelected -> {
                saveReservation(desiredStartTime,desiredEndTime,guests.toInt(),event.room)
            }

            is RoomTypeEvent.SearchRooms -> {
                _statusRoom.value= _statusRoom.value.copy(searchRooms = false)
                searchRoomsAvailables(desiredStartTime,desiredEndTime,guests.toInt())
            }
        }
    }

    fun cleanNavigation() {
        _statusRoom.value=_statusRoom.value.copy(navigateToBookId = -1L)
    }

}
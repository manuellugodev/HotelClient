package com.manuellugodev.hotelmanagment.features.rooms.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.model.Customer
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.reservations.domain.SaveTemporalReservation
import com.manuellugodev.hotelmanagment.features.rooms.domain.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeState
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.profile.usecase.GetDataProfile
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RoomTypeViewModel @Inject constructor(
    var usecase: SearchRoomAvailables,
    var useCaseSaveTemporalReservation: SaveTemporalReservation,
    var useCaseGetMyProfileData: GetDataProfile
) : ViewModel() {

    private val _statusRoom: MutableStateFlow<RoomTypeState> = MutableStateFlow(RoomTypeState())
    val statusRoom:StateFlow<RoomTypeState> = _statusRoom

    fun searchRoomsAvailables(desiredStartTime: Long, desiredEndTime: Long, guests: Int) {

        Log.i("Search","LLamada funcion Search")
        _statusRoom.value=statusRoom.value.copy(showLoader = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = usecase(Date(desiredStartTime), Date(desiredEndTime), guests)

                when (result) {
                    is DataResult.Success -> {
                        _statusRoom.value = statusRoom.value.copy(showRooms = result.data, showLoader = false)
                    }

                    is DataResult.Error -> {
                        _statusRoom.value = statusRoom.value.copy(showError = result.exception.message.toString(), showLoader = false)
                    }
                }
            } catch (e: Exception) {
                _statusRoom.value = statusRoom.value.copy(showError = e.message.toString(), showLoader = false)

            }
        }
    }

    fun saveReservation(
        desiredStartTime: Long,
        desiredEndTime: Long,
        guests: Int,
        roomHotel: RoomHotel
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val customerResult= useCaseGetMyProfileData()
                if(customerResult.isSuccess){
                    val profile=customerResult.getOrThrow()
                    val customer = Customer(1,profile.firstName,profile.lastName,profile.email,profile.phone)
                    val reservationToSave = Reservation(1,customer,roomHotel,desiredStartTime,desiredEndTime,roomHotel.price,0.0,roomHotel.price)

                    val result = useCaseSaveTemporalReservation(reservationToSave)

                    if (result.isSuccess){
                        val reservationSaved=result.getOrThrow()
                        _statusRoom.value=statusRoom.value.copy(navigateToBookId = reservationSaved.id.toLong())
                    }
                }
            } catch (e: Exception) {
                _statusRoom.value = statusRoom.value.copy(showError = "Some is Wrong try again")

            }
        }

    }

    fun onEvent(event: RoomTypeEvent){

        when(event){
            RoomTypeEvent.DismissError -> _statusRoom.value=statusRoom.value.copy(showError = "")
            is RoomTypeEvent.OnClickRoomSelected -> {
                saveReservation(event.desiredStartTime,event.desiredEndTime,event.guests,event.room)
            }
            is RoomTypeEvent.SearchRooms->{

            }
        }
    }
}
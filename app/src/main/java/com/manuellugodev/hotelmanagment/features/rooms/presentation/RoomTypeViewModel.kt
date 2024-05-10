package com.manuellugodev.hotelmanagment.features.rooms.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.model.Customer
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.reservations.domain.SaveTemporalReservation
import com.manuellugodev.hotelmanagment.features.rooms.domain.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeState
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RoomTypeViewModel @Inject constructor(
    var usecase: SearchRoomAvailables,
    var useCaseSaveTemporalReservation: SaveTemporalReservation
) : ViewModel() {

    val _statusRoom: MutableState<RoomTypeState> = mutableStateOf(RoomTypeState.Pending)

    fun searchRoomsAvailables(desiredStartTime: Long, desiredEndTime: Long, guests: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = usecase(Date(desiredStartTime), Date(desiredEndTime), guests)

                when (result) {
                    is DataResult.Success -> {
                        _statusRoom.value = RoomTypeState.Success(result.data)
                    }

                    is DataResult.Error -> {
                        _statusRoom.value = RoomTypeState.Error("Some is wrong,Try Again")
                    }
                }
            } catch (e: Exception) {
                _statusRoom.value = RoomTypeState.Error("Some is wrong,Try Again")

            }
        }
    }

    fun saveReservation(
        desiredStartTime: Long,
        desiredEndTime: Long,
        guests: Int,
        roomHotel: RoomHotel
    ) {


        Log.i("VMROOMTYPE","SaveREservation")
        val customer = Customer(1,"Manuel","Lugo","manuellugo2000ml@gmail.com","7872123124")
        val reservation = Reservation(1,customer,roomHotel,desiredStartTime,desiredEndTime,roomHotel.price,0.0,roomHotel.price)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = useCaseSaveTemporalReservation(reservation)

                when (result) {
                    is DataResult.Success -> {

                        _statusRoom.value = RoomTypeState.RoomSelected(result.data.id.toLong())
                        Log.i("VMROOMTYPE","Success")
                    }

                    is DataResult.Error -> {
                        _statusRoom.value = RoomTypeState.Error("Some is wrong,Try Again")
                    }
                }
            } catch (e: Exception) {
                _statusRoom.value = RoomTypeState.Error("Some is wrong,Try Again")

            }
        }

    }

    fun resetState() {
        _statusRoom.value = RoomTypeState.Pending
    }
}
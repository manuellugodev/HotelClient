package com.manuellugodev.hotelmanagment.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.RoomTypeState
import com.manuellugodev.hotelmanagment.usecases.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RoomTypeViewModel @Inject constructor(var usecase: SearchRoomAvailables) : ViewModel() {

    val _statusRoom: MutableState<RoomTypeState> = mutableStateOf(RoomTypeState.Pending(0))

    fun searchRoomsAvailables(desiredStartTime:Long,desiredEndTime: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = usecase(Date(desiredStartTime),Date(desiredEndTime))

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
}
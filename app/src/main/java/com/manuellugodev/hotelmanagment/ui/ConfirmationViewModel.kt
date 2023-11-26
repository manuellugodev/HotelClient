package com.manuellugodev.hotelmanagment.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.ConfirmationState
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.usecases.SendConfirmationReservation
import com.manuellugodev.hotelmanagment.utils.vo.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(var sendConfirmationReservation: SendConfirmationReservation):ViewModel() {

    val _confirmationScreenState: MutableState<ConfirmationState> = mutableStateOf(ConfirmationState.Pending(0))

    fun sendConfirmation(reservation: Reservation){
        viewModelScope.launch(Dispatchers.Main) {

            _confirmationScreenState.value=ConfirmationState.Pending(0)

            withContext(Dispatchers.IO){

                val result = sendConfirmationReservation.invoke(reservation)

                when(result){

                    is DataResult.Success -> {
                        _confirmationScreenState.value=ConfirmationState.Success(result.data)                    }
                    is DataResult.Error -> {
                        _confirmationScreenState.value=ConfirmationState.Error("Error saving reservation,please try again")
                    }
                }
            }
        }
    }
}
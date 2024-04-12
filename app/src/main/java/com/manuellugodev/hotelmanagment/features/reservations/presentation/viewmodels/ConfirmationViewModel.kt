package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import CONFIRMATION_SCREEN
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetTemporalReservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.SendConfirmationReservation
import com.manuellugodev.hotelmanagment.features.reservations.utils.ConfirmationState
import com.manuellugodev.hotelmanagment.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    var sendConfirmationReservation: SendConfirmationReservation,
    var getTemporalReservation: GetTemporalReservation
) : ViewModel() {

    val _confirmationScreenState: MutableState<ConfirmationState> =
        mutableStateOf(ConfirmationState.Pending)

    fun sendConfirmation(reservation: Reservation) {
        viewModelScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {

                val result = sendConfirmationReservation.invoke(reservation)

                when(result){

                    is DataResult.Success -> {
                        _confirmationScreenState.value =
                            ConfirmationState.SavedReservation(result.data)
                    }
                    is DataResult.Error -> {
                        _confirmationScreenState.value =
                            ConfirmationState.Error(result.exception.message.toString())
                    }
                }
            }
        }
    }

    fun getTemporalReservation(id:Long) {

        Log.i(CONFIRMATION_SCREEN, "getTemporalReservation")
        viewModelScope.launch(Dispatchers.Main) {

            withContext(Dispatchers.IO) {

                val result = getTemporalReservation.invoke(id)

                when (result) {

                    is DataResult.Success -> {
                        _confirmationScreenState.value = ConfirmationState.ShowData(result.data)
                    }

                    is DataResult.Error -> {
                        _confirmationScreenState.value =
                            ConfirmationState.Error("Error saving reservation,please try again")
                    }
                }
            }
        }
    }

    fun resetStates() {
        viewModelScope.launch(Dispatchers.Main) {
            _confirmationScreenState.value = ConfirmationState.Pending
        }

    }
}
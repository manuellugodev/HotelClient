package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import CONFIRMATION_SCREEN
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.DispatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetTemporalReservation
import com.manuellugodev.hotelmanagment.features.reservations.domain.SendConfirmationReservation
import com.manuellugodev.hotelmanagment.features.reservations.utils.ConfirmationState
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.RESERVATION
import com.manuellugodev.hotelmanagment.features.reservations.utils.ConfirmationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    private val sendConfirmationReservation: SendConfirmationReservation,
    private val getTemporalReservation: GetTemporalReservation,
    savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private var temporalId:Long=savedStateHandle.get(RESERVATION)?:-1L

    private val _confirmationState :MutableStateFlow<ConfirmationState> = MutableStateFlow(
        ConfirmationState(searchReservation = true)
    )
    val confirmationState :StateFlow<ConfirmationState> = _confirmationState

    private fun sendConfirmation() {
        viewModelScope.launch(dispatcher.main) {

            withContext(dispatcher.io) {

                confirmationState.value.showReservation?.let { reservation ->
                    val result = sendConfirmationReservation.invoke(reservation)

                    when(result){

                        is DataResult.Success -> {
                            _confirmationState.value=_confirmationState.value.copy(reservationSaved = true) }
                        is DataResult.Error -> {
                            _confirmationState.value =_confirmationState.value.copy(showError = result.exception.message.toString())
                        }
                    }
                } ?: run {
                    _confirmationState.value=_confirmationState.value.copy(showError = "Something went wrong, reservation invalid")
                }

            }
        }
    }

    private fun getTempReservation() {

        viewModelScope.launch(dispatcher.main) {

            withContext(dispatcher.io) {

                val result = getTemporalReservation.invoke(temporalId)

                when (result) {

                    is DataResult.Success -> {
                        _confirmationState.value = confirmationState.value.copy(showReservation = result.data)
                    }

                    is DataResult.Error -> {
                    _confirmationState.value = confirmationState.value.copy(showError = result.exception.message.toString())
                    }
                }
            }
        }
    }

    fun onEvent(confirmationEvent: ConfirmationEvent) {
        when(confirmationEvent){
            ConfirmationEvent.sendConfirmation -> sendConfirmation()
            ConfirmationEvent.getTemporalReservation ->getTempReservation()
        }
    }

}
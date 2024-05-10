package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetMyReservations
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyReservationsViewModel @Inject constructor(val getMyReservationsUseCase: GetMyReservations) :
    ViewModel() {

   private val _stateMyReservation : MutableStateFlow<MyReservationState> = MutableStateFlow(MyReservationState())
   val stateMyReservation :StateFlow<MyReservationState> = _stateMyReservation

   init {
       getMyReservations(1)
   }
    fun getMyReservations(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val result = getMyReservationsUseCase(id)

                withContext(Dispatchers.Main) {
                    if (result is DataResult.Success) {
                        _stateMyReservation.value = stateMyReservation.value.copy(showReservation = result.data)
                    } else {
                        _stateMyReservation.value = stateMyReservation.value.copy(showErrorMsg = (result as DataResult.Error).exception.message?:"Error")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _stateMyReservation.value = stateMyReservation.value.copy(showErrorMsg = e.message?:"Error")}

            }


        }
    }

    fun onEvent(myReservationEvent: MyReservationEvent) {
        when(myReservationEvent){
            MyReservationEvent.OnDismissError -> _stateMyReservation.value= stateMyReservation.value.copy(showErrorMsg = "")
        }
    }

}
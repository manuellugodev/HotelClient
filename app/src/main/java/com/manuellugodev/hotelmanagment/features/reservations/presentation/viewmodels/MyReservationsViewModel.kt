package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetMyReservations
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyReservationsViewModel @Inject constructor(val getMyReservationsUseCase: GetMyReservations,private val distparcher:DistpatcherProvider) :
    ViewModel() {

   private val _stateMyReservation : MutableStateFlow<MyReservationState> = MutableStateFlow(MyReservationState())
   val stateMyReservation :StateFlow<MyReservationState> = _stateMyReservation

   init {
       getMyReservations(1)
   }
    fun getMyReservations(id: Int) {
        viewModelScope.launch(distparcher.io) {

            try {

                val result = getMyReservationsUseCase(id)

                withContext(distparcher.main) {
                    if (result is DataResult.Success) {
                        _stateMyReservation.value = stateMyReservation.value.copy(showReservation = result.data)
                    } else {
                        _stateMyReservation.value = stateMyReservation.value.copy(showErrorMsg = (result as DataResult.Error).exception.message?:"Error")
                    }
                }
            } catch (e: Exception) {
                withContext(distparcher.main) {
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
package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.NavigateSearchParams
import com.manuellugodev.hotelmanagment.features.reservations.utils.ReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.ReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor():ViewModel() {

    private val _stateReservation:MutableStateFlow<ReservationState> = MutableStateFlow(
        ReservationState()
    )
    val stateReservation:StateFlow<ReservationState> = _stateReservation


    fun onEvent(event:ReservationEvent){
        when(event){
            ReservationEvent.DismissError -> {dismissError()}
            is ReservationEvent.NavigateToSearchRooms -> {navigateToSearchRooms(event.startTime,event.endTime,event.guests)}
            is ReservationEvent.OnVisibleDatePicker -> {onVisibleDatePicker(event.visible)}
            is ReservationEvent.OnVisibleGuestComposable -> {onVisibleGuestComposable(event.visible)}
            is ReservationEvent.ShowError -> {showError(event.message)}
            is ReservationEvent.UpdateAdultsCount -> { updateAdultsCount(event.num) }
            is ReservationEvent.UpdateChildrenCount -> {updateChildrenCount(event.num)}
            ReservationEvent.CleanNavigation -> {cleanNavigation()}
        }
    }

    private fun cleanNavigation() {
        _stateReservation.value=stateReservation.value.copy(navigateToSearchRooms = null)
    }

    private fun onVisibleDatePicker(visible: Boolean) {
        _stateReservation.value=stateReservation.value.copy(showDatePicker = visible)
    }

    private fun updateChildrenCount(num: Int) {
        val valueCount=stateReservation.value.numberGuestChildren
        val result=valueCount+num
        if(result>-1){
            _stateReservation.value=stateReservation.value.copy(numberGuestChildren = result)
        }
    }
    private fun updateAdultsCount(num:Int){
        val valueCount=stateReservation.value.numberGuestAdults
        val result=valueCount+num
        if(result>-1){
            _stateReservation.value=stateReservation.value.copy(numberGuestAdults = result)
        }
    }

    private fun showError(message:String) {
        _stateReservation.value=stateReservation.value.copy(showError = message)
    }

    private fun onVisibleGuestComposable(visible: Boolean) {
        _stateReservation.value=stateReservation.value.copy(showGuestSelector = visible)
    }

    private fun dismissError(){
        _stateReservation.value=stateReservation.value.copy(showError = "")
    }

    private fun navigateToSearchRooms(startTime: Long, endTime: Long, guests: Long) {
        val params= NavigateSearchParams(startTime,endTime,guests)
        _stateReservation.value=stateReservation.value.copy(navigateToSearchRooms = params)
    }


}

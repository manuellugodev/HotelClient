package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetMyReservations
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState
import com.manuellugodev.hotelmanagment.domain.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyReservationsViewModel @Inject constructor(var getMyReservationsUseCase: GetMyReservations) :
    ViewModel() {

    val _stateMyReservationScreen: MutableState<MyReservationState> =
        mutableStateOf(MyReservationState.Loading)

    fun getMyReservations(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val result = getMyReservationsUseCase(id)

                withContext(Dispatchers.Main) {
                    if (result is DataResult.Success) {
                        _stateMyReservationScreen.value =
                            MyReservationState.ShowReservation(result.data)
                    } else {
                        _stateMyReservationScreen.value =
                            MyReservationState.Failure(Exception("Error getting result"))

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _stateMyReservationScreen.value =
                        MyReservationState.Failure(Exception("Error getting result"))
                }

            }


        }
    }

}
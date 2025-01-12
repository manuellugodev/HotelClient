package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.DistpatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.AppointmentsNotFound
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetMyReservations
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetPastReservations
import com.manuellugodev.hotelmanagment.features.reservations.domain.GetUpcomingReservations
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyReservationsViewModel @Inject constructor(
    private val getMyReservationsUseCase: GetMyReservations,
    private val getUpcomingReservations: GetUpcomingReservations,
    private val getPastReservations: GetPastReservations,
    private val distparcher: DistpatcherProvider
) :
    ViewModel() {

   private val _stateMyReservation : MutableStateFlow<MyReservationState> = MutableStateFlow(MyReservationState(searchMyReservations = true))
   val stateMyReservation :StateFlow<MyReservationState> = _stateMyReservation

    private fun getReservations(id: Int=1) {
        viewModelScope.launch(distparcher.io) {

            try {

                val result = getMyReservationsUseCase(id)

                withContext(distparcher.main) {
                    if (result is DataResult.Success) {
                        _stateMyReservation.value = stateMyReservation.value.copy(showReservation = result.data, searchMyReservations = false)
                    } else {
                        if ((result as DataResult.Error).exception is AppointmentsNotFound) {
                            _stateMyReservation.value = stateMyReservation.value.copy(
                                showErrorMsg = "Appointments Not Found",
                                searchMyReservations = false
                            )

                        } else {
                            _stateMyReservation.value = stateMyReservation.value.copy(
                                showErrorMsg = (result as DataResult.Error).exception.message
                                    ?: "Error", searchMyReservations = false
                            )

                        }
                    }
                }
            } catch (e: Exception) {
                withContext(distparcher.main) {
                    _stateMyReservation.value = stateMyReservation.value.copy(showErrorMsg = e.message?:"Error", searchMyReservations = false)}

            }


        }
    }

    private fun getUpcomingReservation(id: Int = 1) {
        viewModelScope.launch(distparcher.io) {

            try {

                val result = getUpcomingReservations(id)

                withContext(distparcher.main) {
                    if (result is DataResult.Success) {
                        _stateMyReservation.value = stateMyReservation.value.copy(
                            showReservation = result.data,
                            searchMyReservations = false
                        )
                    } else {
                        if ((result as DataResult.Error).exception is AppointmentsNotFound) {
                            _stateMyReservation.value = stateMyReservation.value.copy(
                                showErrorMsg = "Appointments Not Found",
                                searchMyReservations = false
                            )

                        } else {
                            _stateMyReservation.value = stateMyReservation.value.copy(
                                showErrorMsg = (result as DataResult.Error).exception.message
                                    ?: "Error", searchMyReservations = false
                            )

                        }
                    }
                }
            } catch (e: Exception) {
                withContext(distparcher.main) {
                    _stateMyReservation.value = stateMyReservation.value.copy(
                        showErrorMsg = e.message ?: "Error",
                        searchMyReservations = false
                    )
                }

            }


        }
    }

    private fun getPastReservationVm(id: Int = 1) {
        viewModelScope.launch(distparcher.io) {

            try {

                val result = getPastReservations(id)

                withContext(distparcher.main) {
                    if (result is DataResult.Success) {
                        _stateMyReservation.value = stateMyReservation.value.copy(
                            showReservation = result.data,
                            searchMyReservations = false
                        )
                    } else {
                        if ((result as DataResult.Error).exception is AppointmentsNotFound) {
                            _stateMyReservation.value = stateMyReservation.value.copy(
                                showErrorMsg = "Appointments Not Found",
                                searchMyReservations = false
                            )

                        } else {
                            _stateMyReservation.value = stateMyReservation.value.copy(
                                showErrorMsg = (result as DataResult.Error).exception.message
                                    ?: "Error", searchMyReservations = false
                            )

                        }
                    }
                }
            } catch (e: Exception) {
                withContext(distparcher.main) {
                    _stateMyReservation.value = stateMyReservation.value.copy(
                        showErrorMsg = e.message ?: "Error",
                        searchMyReservations = false
                    )
                }

            }


        }
    }

    fun onEvent(myReservationEvent: MyReservationEvent) {
        when(myReservationEvent){
            MyReservationEvent.OnDismissError -> _stateMyReservation.value= stateMyReservation.value.copy(showErrorMsg = "")
            MyReservationEvent.GetMyReservations -> getReservations()
            MyReservationEvent.GetUpcomingReservations -> {
                _stateMyReservation.value =
                    stateMyReservation.value.copy(optionSelected = 0, showReservation = listOf())
                getUpcomingReservation()
            }

            MyReservationEvent.GetPastReservations -> {
                _stateMyReservation.value =
                    stateMyReservation.value.copy(optionSelected = 1, showReservation = listOf())
                getPastReservationVm()
            }
        }
    }

}
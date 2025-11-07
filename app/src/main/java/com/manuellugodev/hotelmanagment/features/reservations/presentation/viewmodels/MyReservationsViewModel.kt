package com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuellugodev.hotelmanagment.features.core.domain.DispatcherProvider
import com.manuellugodev.hotelmanagment.features.core.domain.exceptions.AppointmentsNotFound
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.DataResult
import com.manuellugodev.hotelmanagment.features.reservations.domain.DeleteReservation
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
    private val removeReservationUsecase: DeleteReservation,
    private val dispatcher: DispatcherProvider
) :
    ViewModel() {

   private val _stateMyReservation : MutableStateFlow<MyReservationState> = MutableStateFlow(MyReservationState(searchMyReservations = true))
   val stateMyReservation :StateFlow<MyReservationState> = _stateMyReservation

    private suspend fun handleReservationResult(fetchBlock: suspend () -> DataResult<List<Reservation>>) {
        try {
            val result = fetchBlock()

            withContext(dispatcher.main) {
                when (result) {
                    is DataResult.Success -> {
                        _stateMyReservation.value = stateMyReservation.value.copy(
                            showReservation = result.data,
                            searchMyReservations = false
                        )
                    }
                    is DataResult.Error -> {
                        handleReservationError(result.exception)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(dispatcher.main) {
                handleReservationError(e)
            }
        }
    }

    private fun handleReservationError(exception: Throwable) {
        val errorMsg = when (exception) {
            is AppointmentsNotFound -> "Appointments Not Found"
            else -> exception.message ?: "Error"
        }
        _stateMyReservation.value = stateMyReservation.value.copy(
            showErrorMsg = errorMsg,
            searchMyReservations = false
        )
    }

    private fun getReservations(id: Int = 1) {
        viewModelScope.launch(dispatcher.io) {
            handleReservationResult { getMyReservationsUseCase(id) }
        }
    }

    private fun getUpcomingReservation(id: Int = 1) {
        viewModelScope.launch(dispatcher.io) {
            handleReservationResult { getUpcomingReservations(id) }
        }
    }

    private fun getPastReservationVm(id: Int = 1) {
        viewModelScope.launch(dispatcher.io) {
            handleReservationResult { getPastReservations(id) }
        }
    }

    private fun deleteReservation() {
        viewModelScope.launch(dispatcher.io) {
            try {
                stateMyReservation.value.reservationSelectedId?.let { reservation ->
                    removeReservationUsecase(reservation.id)


                    withContext(dispatcher.main) {
                        _stateMyReservation.value = stateMyReservation.value.copy(
                            showConfirmDelete = false,
                            reservationSelectedId = null
                        )

                        when (stateMyReservation.value.optionSelected) {
                            0 -> onEvent(MyReservationEvent.GetUpcomingReservations)
                            1 -> onEvent(MyReservationEvent.GetPastReservations)
                        }
                    }

                }
            } catch (e: Exception) {
                withContext(dispatcher.main) {
                    _stateMyReservation.value = stateMyReservation.value.copy(
                        showConfirmDelete = false,
                        reservationSelectedId = null
                    )
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

            is MyReservationEvent.IntentDeleteAppointment -> {
                _stateMyReservation.value = stateMyReservation.value.copy(
                    showConfirmDelete = true,
                    reservationSelectedId = myReservationEvent.reservation
                )
            }

            MyReservationEvent.DismissDeleteAppointment -> {
                _stateMyReservation.value = stateMyReservation.value.copy(
                    showConfirmDelete = false,
                    reservationSelectedId = null
                )
            }

            MyReservationEvent.ConfirmDeleteAppointment -> {
                deleteReservation()
            }
        }
    }

}
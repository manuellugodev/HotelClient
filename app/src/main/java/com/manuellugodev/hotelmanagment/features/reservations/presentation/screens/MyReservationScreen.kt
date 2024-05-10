package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.MyReservationsViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState



@Composable
fun MyReservationScreenRoot(viewModel: MyReservationsViewModel){
    val state by viewModel.stateMyReservation.collectAsState()
    MyReservationScreen(state,viewModel::onEvent)
}
@Composable
fun MyReservationScreen(state:MyReservationState,onEvent:(MyReservationEvent)->Unit) {


    if(state.showReservation.isNotEmpty()){
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(items = state.showReservation) {
                DetailConfirmationScreen(reservation = it)
            }
        }
    }

    if (state.showErrorMsg.isNotEmpty()) {
        ErrorSnackbar(errorMessage = state.showErrorMsg) {
           onEvent(MyReservationEvent.OnDismissError)

        }
    }


}
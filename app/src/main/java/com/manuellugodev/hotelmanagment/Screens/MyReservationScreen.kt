package com.manuellugodev.hotelmanagment.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manuellugodev.hotelmanagment.MyReservationState
import com.manuellugodev.hotelmanagment.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.ui.MyReservationsViewModel
import com.manuellugodev.hotelmanagment.utils.fakes.reservationMock

@Composable
fun MyReservationScreen(viewModel: MyReservationsViewModel = hiltViewModel()) {

    var stateError by remember { mutableStateOf(false) }
    when (val state = viewModel._stateMyReservationScreen.value) {

        is MyReservationState.ShowReservation -> {
            stateError = false
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items(items = state.listReservation) {
                    DetailConfirmationScreen(reservation = it)
                }
            }

        }

        is MyReservationState.Failure -> {
            stateError = true

        }

        is MyReservationState.Loading -> {
            stateError = false
            viewModel.getMyReservations(1)
        }
    }

    if (stateError) {
        ErrorSnackbar(errorMessage = "Error Reservations") {
            stateError = false

        }
    }


}

@Preview
@Composable
fun MyReservationScreenPreview() {

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            DetailConfirmationScreen(reservation = reservationMock)
        }

    }
}

@Composable
fun ItemReservation() {
    Card() {
        Box(
            Modifier
                .height(200.dp)
                .fillMaxWidth(0.9f)
                .background(Color.Blue)
        )

    }
}
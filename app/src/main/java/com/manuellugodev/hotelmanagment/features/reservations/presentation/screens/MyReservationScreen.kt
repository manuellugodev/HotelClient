package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.MyReservationsViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState


@Composable
fun MyReservationScreenRoot(viewModel: MyReservationsViewModel) {
    val state by viewModel.stateMyReservation.collectAsState()
    MyReservationScreen(state, viewModel::onEvent)

    LaunchedEffect(key1 = state.searchMyReservations) {
        if (state.searchMyReservations) {
            viewModel.onEvent(MyReservationEvent.GetMyReservations)
        }

    }
}

@Composable
fun MyReservationScreen(state: MyReservationState, onEvent: (MyReservationEvent) -> Unit) {

    Column {

        val options = listOf("Upcoming", "Past")
        TabRow(selectedTabIndex = state.optionSelected) {

            Tab(
                selected = 0 == state.optionSelected,
                onClick = { onEvent(MyReservationEvent.GetUpcomingReservations) }) {

                Text(text = "Upcoming")
            }
            Tab(
                selected = 1 == state.optionSelected,
                onClick = { onEvent(MyReservationEvent.GetPastReservations) }) {

                Text(text = "Past")
            }


        }

        if (state.showReservation.isNotEmpty()) {
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
}
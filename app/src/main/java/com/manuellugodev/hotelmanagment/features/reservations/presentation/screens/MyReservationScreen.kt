package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manuellugodev.hotelmanagment.R
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.presentation.ui.theme.primaryLight
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.MyReservationsViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState


@Composable
fun MyReservationScreenRoot(viewModel: MyReservationsViewModel) {
    val state by viewModel.stateMyReservation.collectAsState()
    MyReservationScreen(state, viewModel::onEvent)

    LaunchedEffect(key1 = state.searchMyReservations) {
        if (state.searchMyReservations) {
            viewModel.onEvent(MyReservationEvent.GetUpcomingReservations)
        }

    }
}

@Composable
fun MyReservationScreen(state: MyReservationState, onEvent: (MyReservationEvent) -> Unit) {

    Column {
        TabRow(
            selectedTabIndex = state.optionSelected,
            modifier = Modifier.padding(bottom = 10.dp), contentColor = Color.White
        ) {

            Tab(
                modifier = Modifier.padding(bottom = 5.dp),
                selected = 0 == state.optionSelected,
                onClick = { onEvent(MyReservationEvent.GetUpcomingReservations) }) {

                Text(
                    text = "Upcoming",
                    fontSize = 24.sp,
                    color = if (isSystemInDarkTheme()) Color.White else primaryLight
                )
            }
            Tab(
                modifier = Modifier.padding(bottom = 5.dp),
                selected = 1 == state.optionSelected,
                onClick = { onEvent(MyReservationEvent.GetPastReservations) }) {

                Text(
                    text = "Past",
                    fontSize = 24.sp,
                    color = if (isSystemInDarkTheme()) Color.White else primaryLight
                )
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
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        modifier = Modifier.size(100.dp),
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Appointments",
                        colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(Color.White) else null
                    )
                    Text(text = stringResource(id = R.string.not_found_appointments))
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
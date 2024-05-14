package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens

import RESERVATION_SCREEN
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.domain.utils.convertLongToTime
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.ReservationViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.ReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.ReservationState


@Composable
fun ReservationScreenRoot(navController: NavController,viewModel:ReservationViewModel){

    val state by viewModel.stateReservation.collectAsState()
    ReservationScreen(state =state,viewModel::onEvent )

    LaunchedEffect(key1 = state.navigateToSearchRooms) {
        if(state.navigateToSearchRooms!=null){
            navController.navigate(Screen.RoomTypeScreen.withArgs(
                state.navigateToSearchRooms!!.startTime,
                state.navigateToSearchRooms!!.endTime,
                state.navigateToSearchRooms!!.guests))

            viewModel.onEvent(ReservationEvent.CleanNavigation)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    state: ReservationState,
    onEvent:(ReservationEvent)->Unit
) {
    Log.i("Reservation_Screen", "Recomposition")

    val stateDate = rememberDateRangePickerState()

    Column {
        CardFields(stateDate, state.numberGuestAdults,state.numberGuestChildren, event = {
            when (it) {
                EventField.SEARCH -> {}
                EventField.DATE -> {
                    onEvent(ReservationEvent.OnVisibleDatePicker(visible =!state.showDatePicker))
                }

                EventField.PERSON -> {
                    onEvent(ReservationEvent.OnVisibleGuestComposable(visible= !state.showGuestSelector))
                }
            }
        })
        Button(
            modifier = Modifier.padding(start = 10.dp),
            onClick = {
                val startTime = stateDate.selectedStartDateMillis ?: 0
                val endTime = stateDate.selectedEndDateMillis ?: 0
                val guests = (state.numberGuestAdults + state.numberGuestChildren).toLong()

                if (isDateValid(startTime, endTime) && guests != 0L) {
                    val url = Screen.RoomTypeScreen.withArgs(startTime, endTime, guests)

                    Log.i(RESERVATION_SCREEN, "Navigate to ROOm Type")
                    onEvent(ReservationEvent.NavigateToSearchRooms(startTime,endTime,guests))
                } else {
                    onEvent(ReservationEvent.ShowError(message = "Date invalid"))
                }


            }
        ) {
            Text(text = "Search")

        }

    }

    if (state.showDatePicker) {
        DateInputScreen(stateDate) {
            onEvent(ReservationEvent.OnVisibleDatePicker(visible=false))
        }
    }

    if (state.showGuestSelector) {
        Box(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight()
                .padding(bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            BottomSheet(state.numberGuestAdults,state.numberGuestChildren) {event->
                onEvent(event)
            }
        }

    }

    if (state.showError.isNotEmpty()) {
        ErrorSnackbar(errorMessage = "Rellene los datos") {
            onEvent(ReservationEvent.DismissError)
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(stateNumberAdults: Int,stateNumberChildren: Int, onEvent: (ReservationEvent) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onEvent(ReservationEvent.OnVisibleGuestComposable(false)) },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        GuestInputScreen(stateNumberAdults,stateNumberChildren) { event ->
            onEvent(event)
        }
    }
}

@Composable
fun GuestInputScreen(adults:Int,children:Int, event: (ReservationEvent) -> Unit) {

    Column() {
        GuestInputScreen(
            title = "Adults",
            stateNumberGuest = adults,
            operation = {event(ReservationEvent.UpdateAdultsCount(it)) })
        GuestInputScreen(
            title = "Children",
            stateNumberGuest = children,
            operation = { event(ReservationEvent.UpdateChildrenCount(it)) })

        Button(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 10.dp, bottom = 15.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { event(ReservationEvent.OnVisibleGuestComposable(false)) },
        ) {
            Text("Set", textAlign = TextAlign.Center)
        }
    }


}


@Composable
fun GuestInputScreen(title: String, stateNumberGuest: Int, operation: (Int) -> Unit) {

    Box(Modifier.padding(10.dp)) {

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 10.dp),
                text = title, fontSize = 18.sp, textAlign = TextAlign.Left
            )


            Row(
                modifier = Modifier.border(1.dp, Color.Black),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { operation(-1) }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = stateNumberGuest.toString(),
                    fontSize = 28.sp
                )
                IconButton(onClick = { operation(1) }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = ""
                    )
                }

            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputScreen(stateDate: DateRangePickerState, closeEvent: () -> Unit) {
    Card(Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 20.dp)) {
        Column(Modifier.padding(10.dp)) {
            DateRangePicker(modifier = Modifier.weight(8f), state = stateDate)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp),
                onClick = closeEvent
            ) {
                Text("Set", textAlign = TextAlign.Center)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardFields(
    stateDate: DateRangePickerState,
    stateAdultsNumber:Int,
    stateChildrenNumber: Int,
    event: (EventField) -> Unit
) {

    Card(Modifier.padding(10.dp)) {
        Field(
            Icons.Default.DateRange,
            "${convertLongToTime(stateDate.selectedStartDateMillis ?: 0)} - ${
                convertLongToTime(
                    stateDate.selectedEndDateMillis ?: 0
                )
            } ",
            event = { event(EventField.DATE) }
        )
        Field(
            Icons.Filled.Person,
            text = "Adults: $stateAdultsNumber Children : $stateChildrenNumber",
            event = { event(EventField.PERSON) })

    }

}

fun Int.isZero(): String {
    if (this == 0) {
        return ""
    } else {
        return this.toString()
    }
}

enum class EventField() {
    SEARCH,
    DATE,
    PERSON
}


@Composable
fun Field(
    icon: ImageVector,
    text: String = "",
    event: () -> Unit = {},
    textAlign: TextAlign = TextAlign.Center
) {

    Column {
        Row(
            Modifier
                .clickable(onClick = event)
                .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.height(TextFieldDefaults.MinHeight),
                imageVector = icon,
                contentDescription = Icons.Rounded.Search.name
            )
            Text(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                fontSize = 18.sp,
                text = text,
                textAlign = textAlign,
            )
        }

        Divider(thickness = 1.dp, color = Color.Black)
    }

}

fun isDateValid(startDate: Long, endDate: Long): Boolean {
    if (startDate == 0L || endDate == 0L) return false
    if (startDate >= endDate) return false

    return true
}


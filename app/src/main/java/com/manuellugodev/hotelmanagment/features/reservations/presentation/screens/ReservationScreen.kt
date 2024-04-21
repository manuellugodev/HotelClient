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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.reservations.utils.NumberGuest
import com.manuellugodev.hotelmanagment.features.reservations.utils.getSum
import com.manuellugodev.hotelmanagment.features.reservations.utils.getText
import com.manuellugodev.hotelmanagment.features.reservations.utils.numberGuestSaver
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.utils.convertLongToTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    navController: NavController
) {
    Log.i("Reservation_Screen", "Recomposition")
    var dateVisibleState by remember { mutableStateOf(false) }
    var guestVisibleState by remember { mutableStateOf(false) }
    var stateError by remember { mutableStateOf(false) }

    val stateDate = rememberDateRangePickerState()
    val stateNumberGuest by rememberSaveable(stateSaver = numberGuestSaver) {
        mutableStateOf(NumberGuest(mutableStateOf(0), mutableStateOf(0)))
    }
    Column {
        CardFields(stateDate, stateNumberGuest, event = {
            when (it) {
                EventField.SEARCH -> {}
                EventField.DATE -> {
                    dateVisibleState = !dateVisibleState
                }

                EventField.PERSON -> {
                    guestVisibleState = !guestVisibleState
                }
            }
        })
        Button(
            modifier = Modifier.padding(start = 10.dp),
            onClick = {
                val startTime = stateDate.selectedStartDateMillis ?: 0
                val endTime = stateDate.selectedEndDateMillis ?: 0
                val guests = stateNumberGuest.getSum().toLong()

                if (isDateValid(startTime, endTime) && guests != 0L) {
                    val url = Screen.RoomTypeScreen.withArgs(startTime, endTime, guests)

                    Log.i(RESERVATION_SCREEN, "Navigate to ROOm Type")
                    navController.navigate(url)
                } else {
                    stateError = true
                }


            }
        ) {
            Text(text = "Search")

        }

    }

    if (dateVisibleState) {
        DateInputScreen(stateDate) {
            dateVisibleState = dateVisibleState.not()
        }
    }

    if (guestVisibleState) {

        Box(
            Modifier
                .fillMaxWidth(1f)
                .wrapContentHeight()
                .padding(bottom = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            BottomSheet(stateNumberGuest = stateNumberGuest) {
                guestVisibleState = guestVisibleState.not()
            }
        }

    }

    if (stateError) {
        ErrorSnackbar(errorMessage = "Rellene los datos") {
            stateError = false
        }
    }


}

@Preview
@Composable
fun testLayouts() {
    val stateNumberGuest = remember {
        NumberGuest(adults = mutableIntStateOf(0), mutableIntStateOf(0))
    }
    Box(
        Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight()
            .border(2.dp, Color.Black), contentAlignment = Alignment.BottomCenter
    ) {
        GuestInputScreen(stateNumberGuest = stateNumberGuest) {
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(stateNumberGuest: NumberGuest, onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        GuestInputScreen(stateNumberGuest) {
            onDismiss()
        }
    }
}

@Composable
fun GuestInputScreen(stateNumberGuest: NumberGuest, event: () -> Unit) {

    Column() {
        GuestInputScreen(
            title = "Adults",
            stateNumberGuest = stateNumberGuest.adults,
            operation = { if ((stateNumberGuest.adults.value + it) > -1) stateNumberGuest.adults.value += it })
        GuestInputScreen(
            title = "Children",
            stateNumberGuest = stateNumberGuest.children,
            operation = { if ((stateNumberGuest.children.value + it) > -1) stateNumberGuest.children.value += it })

        Button(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 10.dp, bottom = 15.dp)
                .align(Alignment.CenterHorizontally),
            onClick = event,
        ) {
            Text("Set", textAlign = TextAlign.Center)
        }
    }


}


@Composable
fun GuestInputScreen(title: String, stateNumberGuest: State<Int>, operation: (Int) -> Unit) {

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
                    text = stateNumberGuest.value.toString(),
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
fun DateInputScreen(stateDate: DateRangePickerState, event: () -> Unit) {
    Card(Modifier.padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 20.dp)) {
        Column(Modifier.padding(10.dp)) {
            DateRangePicker(modifier = Modifier.weight(8f), state = stateDate)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp),
                onClick = event
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
    stateNumberGuest: NumberGuest,
    event: (EventField) -> Unit
) {

    Card(Modifier.padding(10.dp)) {
        // Field(Icons.Rounded.Search)
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
            text = stateNumberGuest.getText(),
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


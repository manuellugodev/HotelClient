package com.manuellugodev.hotelmanagment.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manuellugodev.hotelmanagment.utils.NumberGuest
import com.manuellugodev.hotelmanagment.utils.convertLongToTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen() {
    Column {
        var dateVisibleState by remember { mutableStateOf(false) }
        var guestVisibleState by remember { mutableStateOf(false) }

        val stateDate = rememberDateRangePickerState()
        val stateNumberGuest by remember {
            mutableStateOf(NumberGuest(mutableStateOf(0), mutableStateOf(0)))
        }
        CardFields(stateDate, event = {
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

        if (dateVisibleState) {
            DateInputScreen(stateDate) {
                dateVisibleState = dateVisibleState.not()
            }
        }

        if (guestVisibleState) {
            GuestInputScreen(stateNumberGuest) {
                guestVisibleState = guestVisibleState.not()
            }
        }

    }


}

@Composable
fun GuestInputScreen(stateNumberGuest: NumberGuest, event: () -> Unit) {


    Card(Modifier.padding(5.dp)){
        Column() {
            GuestInputScreen(
                title = "Adulto",
                stateNumberGuest = stateNumberGuest.adults,
                operation = { if ((stateNumberGuest.adults.value + it) > -1) stateNumberGuest.adults.value += it })
            GuestInputScreen(
                title = "Menores",
                stateNumberGuest = stateNumberGuest.children,
                operation = { if ((stateNumberGuest.children.value + it) > -1) stateNumberGuest.children.value += it })
        }
    }

}



@Composable
fun GuestInputScreen(title: String, stateNumberGuest: State<Int>, operation: (Int) -> Unit) {

    Card(Modifier.padding(10.dp)) {
        Column() {
            Row() {
                Text(text = title, fontSize = 30.sp, textAlign = TextAlign.Center)
                Button(
                    onClick = { operation(-1) }, modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                ) {
                    Text(text = "-", fontSize = 30.sp, modifier = Modifier.wrapContentHeight())
                }
                Box(
                    Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray)
                ) {
                    Text(text = stateNumberGuest.value.toString(), fontSize = 30.sp)
                }
                Button(onClick = { operation(1) }, modifier = Modifier.width(80.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "PLus")
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
fun CardFields(stateDate: DateRangePickerState, event: (EventField) -> Unit) {

    Card(Modifier.padding(10.dp)) {
        Field(Icons.Rounded.Search)
        Field(
            Icons.Default.DateRange,
            "${convertLongToTime(stateDate.selectedStartDateMillis ?: 0)} - ${
                convertLongToTime(
                    stateDate.selectedEndDateMillis ?: 0
                )
            } ",
            event = { event(EventField.DATE) }
        )
        Field(Icons.Filled.Person, event = { event(EventField.PERSON) })

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
    event: () -> Unit = {}
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
                textAlign = TextAlign.Center
            )
        }

        Divider(thickness = 1.dp, color = Color.Black)
    }

}

package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.manuellugodev.hotelmanagment.R
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.core.domain.utils.convertLongToDateTimeRoom
import com.manuellugodev.hotelmanagment.features.core.presentation.ui.theme.delete2
import com.manuellugodev.hotelmanagment.features.core.presentation.ui.theme.primaryLight
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.MyReservationsViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationEvent
import com.manuellugodev.hotelmanagment.features.reservations.utils.MyReservationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs


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
fun ConfirmDeleteDialog(
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Delete") },
        text = { Text("Are you sure you want to delete this item?") },
        confirmButton = {
            Button(onClick = onDelete) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun MyReservationScreen(state: MyReservationState, onEvent: (MyReservationEvent) -> Unit) {

    Box {

        if (state.showConfirmDelete) {
            ConfirmDeleteDialog(
                onDelete = { onEvent(MyReservationEvent.ConfirmDeleteAppointment) },
                onDismiss = { onEvent(MyReservationEvent.DismissDeleteAppointment) }
            )

        }

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
                        DetailMyReservationScreen(it) {
                            onEvent(
                                MyReservationEvent.IntentDeleteAppointment(
                                    it.id
                                )
                            )
                        }
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
}


@Composable
fun DetailMyReservationScreen(reservation: Reservation, onDelete: () -> Unit) {
    val room = reservation.roomHotel

    val checkInTime = reservation.checkIn
    val checkOutTime = reservation.checkOut

    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val threshold = 300f // Threshold for deletion

    val visibleBox = remember { mutableStateOf(true) }

    Box {

        Box(
            Modifier
                .fillMaxWidth(0.5f)
                .height(380.dp)
                .padding(vertical = 8.dp, horizontal = 20.dp)
                .background(delete2)
                .align(Alignment.CenterEnd)
        ) {

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = Icons.Default.Delete.name,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 48.dp)
                    .size(50.dp)
            )
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .modifierForSlide(scope, offsetX, threshold, onDelete)


        ) {
            Column(Modifier.padding(10.dp)) {
                AsyncImage(
                    model = room.pathImage,
                    contentDescription = room.description,
                    Modifier
                        .height(200.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillBounds
                )


                Text(

                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp),
                    text = room.description,
                    fontSize = 24.sp
                )


                Row {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Check In")
                    Text("Check in :  " + convertLongToDateTimeRoom(checkInTime))
                }

                Row {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Check Out")
                    Text("Check Out :  " + convertLongToDateTimeRoom(checkOutTime))
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = "Total Price"
                    )
                    Text(text = "Total: ${reservation.totalPrice}")
                }


            }

        }
    }
}


fun Modifier.modifierForSlide(
    scope: CoroutineScope,
    offsetX: Animatable<Float, AnimationVector1D>,
    threshold: Float,
    onDelete: () -> Unit
): Modifier {


    return this
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onHorizontalDrag = { _, dragAmount ->
                    scope.launch {
                        // Update offset during drag
                        if (offsetX.value + dragAmount > 0) return@launch
                        offsetX.snapTo(offsetX.value + dragAmount)
                    }
                },
                onDragEnd = {
                    scope.launch {
                        // Animate to dismiss if dragged beyond threshold
                        if (abs(offsetX.value) > threshold) {
                            val target = if (offsetX.value > 0) 500f else -500f
                            offsetX.animateTo(
                                targetValue = target,
                                animationSpec = tween(durationMillis = 300)
                            )
                            onDelete()
                        } else {
                            // Reset to original position
                            offsetX.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 300)
                            )
                        }
                    }
                }
            )
        }
        .offset { IntOffset(offsetX.value.toInt(), 0) }

}


@Composable
fun SlideToDeleteCard(
    text: String,
    onDelete: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val threshold = 300f // Threshold for deletion


    Box(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(100.dp) // Ensure the same height for the card and background
    ) {
        // Background "Remove" Button
        Box(
            Modifier
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(16.dp))
                .height(100.dp)
                .padding(8.dp)
                .background(Color.Red)
                .align(Alignment.CenterEnd)
                .clickable {
                    scope.launch {
                        offsetX.animateTo(
                            targetValue = -1000f,
                            animationSpec = tween(durationMillis = 300)
                        )
                        onDelete()
                    }
                }
        ) {
            Text(
                text = "Remove",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                // Update offset during drag
                                offsetX.snapTo(offsetX.value + dragAmount)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                // Animate to dismiss if dragged beyond threshold
                                if (abs(offsetX.value) > threshold) {
                                    val target = if (offsetX.value > 0) 500f else -500f
                                    offsetX.animateTo(
                                        targetValue = target,
                                        animationSpec = tween(durationMillis = 300)
                                    )
                                    onDelete()
                                } else {
                                    // Reset to original position
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(durationMillis = 300)
                                    )
                                }
                            }
                        }
                    )
                }
                .offset { IntOffset(offsetX.value.toInt(), 0) } // Apply horizontal offset
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .size(100.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
            }
        }
    }
}

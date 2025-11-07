package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.manuellugodev.hotelmanagment.features.reservations.utils.ReservationFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

// Constants for better maintainability
private const val SWIPE_THRESHOLD = 150f
private const val ANIMATION_DURATION = 300

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
        title = {
            Text(
                "Confirm Delete",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                "Are you sure you want to delete this reservation? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = onDelete,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
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
            // Enhanced Tab Row
            TabRow(
                selectedTabIndex = state.optionSelected.value,
                modifier = Modifier.padding(bottom = 16.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    modifier = Modifier.padding(vertical = 12.dp),
                    selected = ReservationFilter.UPCOMING == state.optionSelected,
                    onClick = { onEvent(MyReservationEvent.GetUpcomingReservations) }
                ) {
                    Text(
                        text = "Upcoming",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (ReservationFilter.UPCOMING == state.optionSelected)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
                Tab(
                    modifier = Modifier.padding(vertical = 12.dp),
                    selected = ReservationFilter.PAST == state.optionSelected,
                    onClick = { onEvent(MyReservationEvent.GetPastReservations) }
                ) {
                    Text(
                        text = "Past",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (ReservationFilter.PAST == state.optionSelected)
                            FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            if (state.showReservation.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = state.showReservation) { reservation ->
                        DetailMyReservationScreen(reservation) {
                            onEvent(MyReservationEvent.IntentDeleteAppointment(reservation))
                        }
                    }
                }
            } else {
                // Enhanced Empty State
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Hotel,
                            contentDescription = "No Reservations",
                            modifier = Modifier.size(120.dp),
                            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Reservations Found",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (state.optionSelected == ReservationFilter.UPCOMING)
                                "You don't have any upcoming reservations"
                            else "You don't have any past reservations",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
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

    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Minimalist Delete Background
        Box(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .height(180.dp)
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.error)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            )
        }

        // Minimalist Horizontal Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .modifierForSlide(scope, offsetX, SWIPE_THRESHOLD, onDelete),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                // Full width horizontal image
                AsyncImage(
                    model = room.pathImage,
                    contentDescription = room.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )

                // Minimalist content section
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Room Type (title)
                    Text(
                        text = room.roomType,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Check-in and Check-out with icons
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Check-in",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = convertLongToDateTimeRoom(checkInTime),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Check-out",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = convertLongToDateTimeRoom(checkOutTime),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Bottom row: Guests and Price with icons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Guests",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${room.peopleQuantity} Guests",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "$${reservation.totalPrice}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactReservationInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

@Composable
private fun ReservationInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
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
                        // Only allow left swipe (negative values)
                        if (offsetX.value + dragAmount > 0) return@launch
                        offsetX.snapTo(offsetX.value + dragAmount)
                    }
                },
                onDragEnd = {
                    scope.launch {
                        // Animate to dismiss if dragged beyond threshold
                        if (abs(offsetX.value) > threshold) {
                            offsetX.animateTo(
                                targetValue = -1000f,
                                animationSpec = tween(durationMillis = ANIMATION_DURATION)
                            )
                            onDelete()
                        } else {
                            // Reset to original position with smooth animation
                            offsetX.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = ANIMATION_DURATION)
                            )
                        }
                    }
                }
            )
        }
        .offset { IntOffset(offsetX.value.toInt(), 0) }
}

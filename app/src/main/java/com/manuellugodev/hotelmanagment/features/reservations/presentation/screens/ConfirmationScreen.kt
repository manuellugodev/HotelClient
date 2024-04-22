package com.manuellugodev.hotelmanagment.features.reservations.presentation.screens

import CONFIRMATION_SCREEN
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.ConfirmationViewModel
import com.manuellugodev.hotelmanagment.features.reservations.utils.ConfirmationState
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.utils.convertLongToDateTimeRoom


@SuppressLint("RememberReturnType")
@Composable
fun ConfirmationScreen(
    navController: NavHostController,
    viewModel: ConfirmationViewModel = hiltViewModel(),
    reservationId: Int
) {

    Log.i(CONFIRMATION_SCREEN, "Recomposition")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    Color.White
                ), startY = 40f
            )
        )
    ) {
        when (val state = viewModel._confirmationScreenState.value) {

            is ConfirmationState.ShowData -> {
                Log.i("Confirmation_Screen", "Show_Data")

                val reservation = state.dataReservation

                Log.i("Confirmation_Screen", reservation.toString())
                DetailConfirmationScreenNew(reservation)
                Button(
                    onClick = { viewModel.sendConfirmation(reservation) },
                    Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(text = "Book")
                }
            }

            is ConfirmationState.SavedReservation -> {
                Log.i("Confirmation_Screen", "Saved_Data")
                navController.popBackStack(Screen.ReservationScreen.route, inclusive = true)
                viewModel.resetStates()
            }

            is ConfirmationState.Error -> {
                Log.i("Confirmation_Screen", "Error : " + state.message)
                Text(text = state.message)
            }

            is ConfirmationState.Pending -> {
                Log.i("Confirmation_Screen", "PEnding")
                viewModel.getTemporalReservation(reservationId.toLong())
            }

        }

    }
}


@Composable
fun DetailConfirmationScreen(reservation: Reservation) {
    Log.i(CONFIRMATION_SCREEN, "Detail show data")
    val room = reservation.roomHotel

    val checkInTime = reservation.checkIn
    val checkOutTime = reservation.checkOut

    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
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


        }

    }
}


@Composable
fun DetailConfirmationScreenNew(reservation: Reservation) {

    Log.i(CONFIRMATION_SCREEN, "Detail show data")
    val room = reservation.roomHotel

    val checkInTime = reservation.checkIn
    val checkOutTime = reservation.checkOut

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

                .fillMaxHeight(0.5f)
                .background(Color.Red)
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), model = room.pathImage,
                contentDescription = room.description,
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = room.description,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )

        }
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(top = 20.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")

                Text(text = "Check-In")
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    text = convertLongToDateTimeRoom(checkInTime)
                )

                HorizontalDivider(Modifier.fillMaxWidth(0.8f))
            }
            VerticalDivider(Modifier.padding(bottom = 40.dp, top = 10.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                Text(text = "Check-Out")
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    text = convertLongToDateTimeRoom(checkOutTime)
                )
                HorizontalDivider(Modifier.fillMaxWidth(0.8f))
            }
        }
        Icon(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally),
            imageVector = Icons.Default.MonetizationOn,
            contentDescription = ""
        )

        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(modifier = Modifier.padding(bottom = 10.dp), text = "Price")
                Text(modifier = Modifier.padding(bottom = 10.dp), text = "Tax")


            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = reservation.price.toString()
                )
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = reservation.taxPrice.toString()
                )
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(
                modifier = Modifier
                    .size(40.dp),
                imageVector = Icons.Default.MonetizationOn,
                contentDescription = ""
            )
            Text(
                text = reservation.totalPrice.toString(),
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
        }


    }

}

val subtitle = 10.sp
const val RESERVATION = "temporal_reservation"

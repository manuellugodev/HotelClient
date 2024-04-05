package com.manuellugodev.hotelmanagment.Screens

import CONFIRMATION_SCREEN
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.manuellugodev.hotelmanagment.ConfirmationState
import com.manuellugodev.hotelmanagment.domain.model.Reservation
import com.manuellugodev.hotelmanagment.ui.ConfirmationViewModel
import com.manuellugodev.hotelmanagment.utils.convertLongToDateTimeRoom
import com.manuellugodev.hotelmanagment.utils.fakes.reservationMock


@SuppressLint("RememberReturnType")
@Composable
fun ConfirmationScreen(
    navController: NavHostController,
    viewModel: ConfirmationViewModel = hiltViewModel(),
    reservationId: Int
) {

    Log.i(CONFIRMATION_SCREEN,"Recomposition")
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Log.i("Confirmation_Screen","Recomposition")
        when(val state=viewModel._confirmationScreenState.value){

            is ConfirmationState.ShowData -> {
                Log.i("Confirmation_Screen","Show_Data")
                val reservation=state.dataReservation
                DetailConfirmationScreen(reservation)
            }

            is ConfirmationState.SavedReservation -> {
                Log.i("Confirmation_Screen","Saved_Data")
                navController.popBackStack()
            }
            is ConfirmationState.Error -> {
                Log.i("Confirmation_Screen","Error")
            }
            is ConfirmationState.Pending -> {
                Log.i("Confirmation_Screen","PEnding")
               viewModel.getTemporalReservation(reservationId.toLong())
            }

        }

        Button(onClick = { viewModel.sendConfirmation(reservationMock)}, Modifier.fillMaxWidth(0.7f)) {
            Text(text = "Book")
        }
    }
}

@Preview
@Composable
fun ConfirmationScreenPreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DetailConfirmationScreen(reservationMock)

        Button(onClick = TODO(), Modifier.fillMaxWidth(0.7f)) {
            Text(text = "Book")
        }
    }
}

@Composable
fun DetailConfirmationScreen(reservation: Reservation) {
    Log.i(CONFIRMATION_SCREEN,"Detail show data")
    val room = reservation.roomHotel

    val checkInTime= reservation.checkIn
    val checkOutTime = reservation.checkOut

    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            AsyncImage(
                model = room.pathImage,
                contentDescription = room.description?:"Hoa",
                Modifier.height(200.dp)
            )


            Text(
                room.description,
                fontSize = 24.sp
            )

            Text("Check in :  " + convertLongToDateTimeRoom(checkInTime))
            Text("Check out : "+ convertLongToDateTimeRoom(checkOutTime))

            Card(
                shape = RoundedCornerShape(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        2.dp,
                        Color.Black, RoundedCornerShape(2.dp)
                    )
                    .padding(5.dp)

            ) {
                Column {


                    Text(
                        text = "Price: ${reservation.price}$",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = "tax: ${reservation.taxPrice}$",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(text = "Total   ${reservation.totalPrice}$", fontSize = 18.sp)

                }

            }


        }

    }
}

val subtitle = 10.sp
const val RESERVATION="temporal_reservation"

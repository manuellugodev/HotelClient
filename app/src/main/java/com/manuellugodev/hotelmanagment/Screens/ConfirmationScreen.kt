package com.manuellugodev.hotelmanagment.Screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.type.DateTime
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.utils.convertLongToDateTimeRoom
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun ConfirmationScreen(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DetailConfirmationScreen()

        Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth(0.7f)) {
            Text(text = "Book")
        }
    }
}

@Preview
@Composable
fun ConfirmationScreenPreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DetailConfirmationScreen()

        Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth(0.7f)) {
            Text(text = "Book")
        }
    }
}

@Composable
fun DetailConfirmationScreen() {
    val room = RoomHotel(
        "test",
        2,
        "https://cdn.loewshotels.com/loewshotels.com-1435110691/cms/cache/v2/5f5a6e0d12749.jpg/1920x1080/fit/80/2d2d9d187a62f65b7602eab28e06bcce.jpg",
        2,
        200.0
    )

    val checkInTime= 1700103111000
    val checkOutTime = 1700106711000

    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            AsyncImage(
                model = room.title,
                contentDescription = room.pathImage, Modifier.height(200.dp)
            )


            Text(
                room.title,
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
                        text = "price:  100$",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = "tax      10$",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(text = "Total   110$", fontSize = 18.sp)

                }

            }


        }

    }
}

val subtitle = 10.sp

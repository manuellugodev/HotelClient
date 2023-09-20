package com.manuellugodev.hotelmanagment.Screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Preview
@Composable
fun ConfirmationScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        DetailConfirmationScreen()

        Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth(0.7f)) {
            Text(text = "Book")
        }
    }
}

@Composable
fun DetailConfirmationScreen(){
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            AsyncImage(
                model = "https://cdn.loewshotels.com/loewshotels.com-1435110691/cms/cache/v2/5f5a6e0d12749.jpg/1920x1080/fit/80/2d2d9d187a62f65b7602eab28e06bcce.jpg",
                contentDescription = "", Modifier.height(200.dp)
            )


            Text(
                "Room Name",
                fontSize = 24.sp
            )

            Text("Check in :  17 dic 2025")
            Text("Check out :  17 dic 2025")

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
                        color=Color.LightGray
                    )
                    Text(text = "Total   110$", fontSize = 18.sp)

                }

            }




        }

    }
}

val subtitle = 10.sp

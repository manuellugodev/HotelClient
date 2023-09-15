package com.manuellugodev.hotelmanagment.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BedroomParent
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manuellugodev.hotelmanagment.R
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel

@Preview
@Composable
fun RoomTypeScreen() {
    val room = RoomHotel("Room double", 2, "", 2, 60.23)
    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        items(10) {
            RoomItem()
        }
    }
}

@Preview
@Composable
fun RoomItem() {
    val room = RoomHotel("Room double", 2, "", 2, 60.23)

    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(Modifier.padding(15.dp)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(contentScale = ContentScale.Inside,painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "image")
            }

            Text(text = room.title, fontSize = 30.sp, textAlign = TextAlign.Left)

            Row() {
                Icon(
                    imageVector = Icons.Rounded.Bed,
                    contentDescription = Icons.Rounded.Person.name
                )
                Text(text = room.numberOfBeds.toString())
            }
            Row() {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = Icons.Rounded.Person.name
                )
                Text(text = room.peopleQuantity.toString())
            }
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                Text(text = "Price", Modifier.padding(horizontal = 20.dp), fontSize = 22.sp)

                Text(text = room.price.toString(), fontSize = 22.sp, textAlign = TextAlign.Right)
            }
        }
    }
}

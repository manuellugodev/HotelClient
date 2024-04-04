package com.manuellugodev.hotelmanagment.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.manuellugodev.hotelmanagment.RoomTypeState
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.ui.RoomTypeViewModel


@Composable
fun RoomTypeScreen(
    navController: NavController,
    desiredStartTime: Long,
    desiredEndTime: Long,
    guests:Int,
    viewModel: RoomTypeViewModel = hiltViewModel() ) {

    LazyColumn(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (viewModel._statusRoom.value) {
            is RoomTypeState.Error -> {
                item { Text(text = "Error get lists") }
            }

            is RoomTypeState.Pending -> {
                item { CircularProgressIndicator() }
                viewModel.searchRoomsAvailables(desiredStartTime,desiredEndTime,guests)
            }

            is RoomTypeState.Success -> {
                val list = (viewModel._statusRoom.value as RoomTypeState.Success).data
                items(items = list) {
                    RoomItem(room = it) { navController.navigate(Screen.ConfirmationScreen.route)  }
                }

            }
        }
    }
}

@Composable
private fun RoomItem(room: RoomHotel,onClickItem:()->Unit) {

    Card(
        Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .clickable { onClickItem() }
    ) {
        Column(Modifier.padding(15.dp)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    model =room.pathImage,
                    contentDescription = room.description,
                    contentScale = ContentScale.FillBounds,

                    )
            }

            Text(text = room.description, fontSize = 30.sp, textAlign = TextAlign.Left)

            Row() {
                Icon(
                    imageVector = Icons.Rounded.Bed,
                    contentDescription = Icons.Rounded.Person.name
                )
                Text(text = room.roomType.toString())
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

const val START_TIME = "startTime"
const val END_TIME = "endTime"
const val GUESTS="guests"

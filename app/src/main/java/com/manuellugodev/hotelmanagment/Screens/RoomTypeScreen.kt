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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BedroomParent
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manuellugodev.hotelmanagment.R
import com.manuellugodev.hotelmanagment.RoomTypeState
import com.manuellugodev.hotelmanagment.data.RoomRepository
import com.manuellugodev.hotelmanagment.data.RoomRepositoryImpl
import com.manuellugodev.hotelmanagment.data.sources.RoomDataSourceFirebaseOperations
import com.manuellugodev.hotelmanagment.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.ui.RoomTypeViewModel
import com.manuellugodev.hotelmanagment.usecases.SearchRoomAvailables
import com.manuellugodev.hotelmanagment.utils.vo.DataResult


@Composable
fun RoomTypeScreen(navController: NavController) {

    val viewModel = remember {
        RoomTypeViewModel(
            SearchRoomAvailables(
                RoomRepositoryImpl(
                    RoomDataSourceFirebaseOperations(
                        Firebase.firestore
                    )
                )
            )
        )
    }
    val room = RoomHotel("Room double", 2, "", 2, 60.23)

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
                viewModel.searchRoomsAvailables(0)
            }

            is RoomTypeState.Success -> {
                val list = (viewModel._statusRoom.value as RoomTypeState.Success).data
                items(items = list) {
                    RoomItem(room = it)
                }

            }
        }
    }
}


@Composable
private fun RoomItem(room: RoomHotel) {

    Card(
        Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
    ) {
        Column(Modifier.padding(15.dp)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    model =room.pathImage,
                    contentDescription = room.title,
                    contentScale = ContentScale.Crop,

                    )
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

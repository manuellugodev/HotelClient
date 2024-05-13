package com.manuellugodev.hotelmanagment.features.rooms.presentation

import ROOMTYPE_SCREEN
import android.util.Log
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
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.manuellugodev.hotelmanagment.features.core.composables.ErrorSnackbar
import com.manuellugodev.hotelmanagment.features.core.domain.model.RoomHotel
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeState
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.rooms.utils.RoomTypeEvent


@Composable
fun RoomTypeScreenRoot(
    navController: NavController,
    viewModel: RoomTypeViewModel,
) {
    val state by viewModel.statusRoom.collectAsState()
    RoomTypeScreen(
        viewModel::onEvent,
        state = state
    )
    LaunchedEffect(state.navigateToBookId){
        if(state.navigateToBookId!=-1L){
            navController.navigate(Screen.ConfirmationScreen.withArgs(state.navigateToBookId))
            viewModel.cleanNavigation()
        }
    }
}

@Composable
fun RoomTypeScreen(
    onEvent: (RoomTypeEvent) -> Unit,
    state: RoomTypeState
) {
    Box (contentAlignment = Alignment.Center){

        LazyColumn(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = state.showRooms) {room->
                RoomItem(room = room) {
                    onEvent(
                        RoomTypeEvent.OnClickRoomSelected(room)
                    )
                }
            }
        }
        if (state.showLoader) {
            CircularProgressIndicator()
        }
    }

    if(state.showError.isNotEmpty()){
        ErrorSnackbar(errorMessage = state.showError) {
            onEvent(RoomTypeEvent.DismissError)
        }
    }


}

@Composable
private fun RoomItem(room: RoomHotel, onClickItem: () -> Unit) {

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
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    model = room.pathImage,
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
                Icon(
                    imageVector = Icons.Rounded.AttachMoney,
                    contentDescription = Icons.Rounded.AttachMoney.name
                )

                Text(text = room.price.toString(), textAlign = TextAlign.Right)
            }
        }
    }
}

const val START_TIME = "startTime"
const val END_TIME = "endTime"
const val GUESTS = "guests"

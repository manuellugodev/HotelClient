package com.manuellugodev.hotelmanagment.features.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.navigation.Screen
import com.manuellugodev.hotelmanagment.navigation.navigateAndCleanBackStack


@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {

    val state = viewModel.stateProfile

    LaunchedEffect(state.value is ProfileState.init) {
        viewModel.loadDataProfile()
    }
    Box(
        Modifier
            .fillMaxSize(1f)
    ) {


        Column(
            Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.value is ProfileState.ShowProfile) {
                showDataProfile((state.value as ProfileState.ShowProfile).name)
            }

            Button(
                modifier = Modifier
                    .wrapContentSize(),
                onClick = { viewModel.logOutSession() }) {
                Text(text = "Log out")
            }
        }

        if (state.value is ProfileState.logOut) {
            navController.navigateAndCleanBackStack(Screen.WelcomeScreen.route)
        }

    }
}

@Composable
fun showDataProfile(data: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize()
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = data, fontSize = 20.sp
        )
    }
}

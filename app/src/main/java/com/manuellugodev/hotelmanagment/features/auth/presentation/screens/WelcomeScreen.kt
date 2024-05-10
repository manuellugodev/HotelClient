package com.manuellugodev.hotelmanagment.features.auth.presentation.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels.WelcomeVM
import com.manuellugodev.hotelmanagment.features.auth.utils.WelcomeState
import com.manuellugodev.hotelmanagment.features.core.navigation.Screen
import com.manuellugodev.hotelmanagment.features.core.navigation.navigateAndCleanBackStack

@Composable
fun WelcomeScreen(navHost: NavController, viewModel: WelcomeVM = hiltViewModel()) {

    val state by viewModel.stateWelcome

    if (state is WelcomeState.IsLogged) {
        navHost.navigateAndCleanBackStack(Screen.ReservationScreen.route)
    }

    if (state is WelcomeState.Error) {
        navHost.navigateAndCleanBackStack(Screen.LoginScreen.route)

    }
    if (state is WelcomeState.Loading) {
        CircularProgressIndicator()
        viewModel.checkUser()
    }

}
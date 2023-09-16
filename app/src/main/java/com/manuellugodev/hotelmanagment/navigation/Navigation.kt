package com.manuellugodev.hotelmanagment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manuellugodev.hotelmanagment.Screens.ReservationScreen
import com.manuellugodev.hotelmanagment.Screens.RoomTypeScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination =Screen.ReservationScreen.route){
        composable(route =Screen.ReservationScreen.route){
            ReservationScreen(navController)
        }

        composable(route = Screen.RoomTypeScreen.route){
            RoomTypeScreen()
        }
    }
}
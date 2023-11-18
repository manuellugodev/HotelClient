package com.manuellugodev.hotelmanagment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manuellugodev.hotelmanagment.Screens.ConfirmationScreen
import com.manuellugodev.hotelmanagment.Screens.LoginScreen
import com.manuellugodev.hotelmanagment.Screens.ReservationScreen
import com.manuellugodev.hotelmanagment.Screens.RoomTypeScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination =Screen.LoginScreen.route){
        composable(route=Screen.LoginScreen.route){
            LoginScreen(navController)
        }

        composable(route =Screen.ReservationScreen.route){
            ReservationScreen(navController)
        }

        composable(route = Screen.RoomTypeScreen.route){
            RoomTypeScreen(navController)
        }
        composable(route = Screen.ConfirmationScreen.route){
            ConfirmationScreen(navController)
        }
    }
}
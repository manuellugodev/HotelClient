package com.manuellugodev.hotelmanagment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manuellugodev.hotelmanagment.Screens.ConfirmationScreen
import com.manuellugodev.hotelmanagment.Screens.END_TIME
import com.manuellugodev.hotelmanagment.Screens.GUESTS
import com.manuellugodev.hotelmanagment.Screens.LoginScreen
import com.manuellugodev.hotelmanagment.Screens.ReservationScreen
import com.manuellugodev.hotelmanagment.Screens.RoomTypeScreen
import com.manuellugodev.hotelmanagment.Screens.START_TIME

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = Screen.ReservationScreen.route) {
            ReservationScreen(navController)
        }

        composable(route = Screen.RoomTypeScreen.route + "/{$START_TIME}/{$END_TIME}/{$GUESTS}",
            arguments = listOf(
                navArgument(START_TIME){
                    type = NavType.LongType
                    defaultValue = 0L
                },
                navArgument(END_TIME){
                    type = NavType.LongType
                    defaultValue = 0L
                },
                navArgument(GUESTS){
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {

            val desiredStartTime = it.arguments?.getLong(START_TIME)?:0L
            val desiredEndTime = it.arguments?.getLong(END_TIME)?:0L
            val guests = it.arguments?.getLong(GUESTS)?:0
            RoomTypeScreen(navController, desiredStartTime, desiredEndTime,guests.toInt())


        }
        composable(route = Screen.ConfirmationScreen.route) {
            ConfirmationScreen(navController)
        }
    }
}
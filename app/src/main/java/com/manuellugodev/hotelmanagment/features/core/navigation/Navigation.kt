package com.manuellugodev.hotelmanagment.features.core.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.manuellugodev.hotelmanagment.features.auth.presentation.screens.LoginScreen
import com.manuellugodev.hotelmanagment.features.auth.presentation.screens.RegisterScreenRoot
import com.manuellugodev.hotelmanagment.features.auth.presentation.screens.WelcomeScreen
import com.manuellugodev.hotelmanagment.features.auth.presentation.viewmodels.RegisterViewModel
import com.manuellugodev.hotelmanagment.features.profile.presentation.ProfileScreenRoot
import com.manuellugodev.hotelmanagment.features.profile.presentation.ProfileViewModel
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.ConfirmationScreen
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.ConfirmationScreenRoot
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.MyReservationScreen
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.MyReservationScreenRoot
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.RESERVATION
import com.manuellugodev.hotelmanagment.features.reservations.presentation.screens.ReservationScreen
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.ConfirmationViewModel
import com.manuellugodev.hotelmanagment.features.reservations.presentation.viewmodels.MyReservationsViewModel
import com.manuellugodev.hotelmanagment.features.rooms.presentation.END_TIME
import com.manuellugodev.hotelmanagment.features.rooms.presentation.GUESTS
import com.manuellugodev.hotelmanagment.features.rooms.presentation.RoomTypeScreen
import com.manuellugodev.hotelmanagment.features.rooms.presentation.RoomTypeScreenRoot
import com.manuellugodev.hotelmanagment.features.rooms.presentation.RoomTypeViewModel
import com.manuellugodev.hotelmanagment.features.rooms.presentation.START_TIME


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route) {

        composable(route = Screen.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = Screen.ReservationScreen.route) {
            ReservationScreen(navController)
        }

        composable(
            route = Screen.RoomTypeScreen.route + "/{$START_TIME}/{$END_TIME}/{$GUESTS}",
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
        ) {backStackEntry ->
            val viewModel:RoomTypeViewModel= hiltViewModel(backStackEntry)
            RoomTypeScreenRoot(navController, viewModel)

        }
        composable(route = Screen.ConfirmationScreen.route + "/{$RESERVATION}",
            arguments = listOf(
                navArgument(RESERVATION) {
                    type = NavType.LongType
                }
            )
        ) {
            val reservation = it.arguments?.getLong(RESERVATION) ?: 1
            val viewModel:ConfirmationViewModel= hiltViewModel()
            viewModel.setIdTemporal(reservation)
            ConfirmationScreenRoot(viewModel = viewModel,navController=navController)
        }
        composable(route = Screen.MyReservationsScreen.route) {
           val myReservationVM :MyReservationsViewModel= hiltViewModel()
            MyReservationScreenRoot(myReservationVM)
        }

        composable(route = Screen.MyProfileScreen.route) {
            val profileViewModel:ProfileViewModel = hiltViewModel()
            ProfileScreenRoot(navController = navController, viewModel = profileViewModel)
        }

        composable(route = Screen.RegisterScreen.route){
            val viewModel:RegisterViewModel = hiltViewModel()
            RegisterScreenRoot(navController = navController, viewModel = viewModel)
        }
    }
}
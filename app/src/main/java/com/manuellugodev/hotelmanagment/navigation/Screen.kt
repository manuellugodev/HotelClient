package com.manuellugodev.hotelmanagment.navigation

sealed class Screen(val route:String){
    object WelcomeScreen : Screen("welcome_screen")
    object LoginScreen : Screen("login_screen")
    object ReservationScreen : Screen("reservation_screen")

    object RoomTypeScreen : Screen("room_type_reservation")

    object ConfirmationScreen : Screen("room_reservation_confirm")

    object MyReservationsScreen : Screen("my_reservation")

    object MyProfileScreen : Screen("my_profile")

    fun withArgs(vararg args: Long): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

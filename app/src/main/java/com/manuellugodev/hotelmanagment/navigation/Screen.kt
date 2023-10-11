package com.manuellugodev.hotelmanagment.navigation

sealed class Screen(val route:String){
    object LoginScreen:Screen("login_screen")
    object ReservationScreen:Screen("reservation_screen")

    object RoomTypeScreen:Screen("room_type_reservation")
}

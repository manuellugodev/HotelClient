package com.manuellugodev.hotelmanagment.network.entities

data class GuestApi(
    var guestId: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = ""
)

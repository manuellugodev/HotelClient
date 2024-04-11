package com.manuellugodev.hotelmanagment.network.entities

data class LoginResponse(
    val token: String,
    val message: String?
)

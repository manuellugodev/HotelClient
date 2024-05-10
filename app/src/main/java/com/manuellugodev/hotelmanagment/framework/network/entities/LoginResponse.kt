package com.manuellugodev.hotelmanagment.framework.network.entities

data class LoginResponse(
    val token: String,
    val message: String?
)

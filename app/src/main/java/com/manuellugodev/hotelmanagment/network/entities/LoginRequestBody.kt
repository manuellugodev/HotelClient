package com.manuellugodev.hotelmanagment.network.entities

data class LoginRequestBody(
    private val username: String,
    private val password: String
)

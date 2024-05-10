package com.manuellugodev.hotelmanagment.framework.network.entities

data class LoginRequestBody(
    private val username: String,
    private val password: String
)

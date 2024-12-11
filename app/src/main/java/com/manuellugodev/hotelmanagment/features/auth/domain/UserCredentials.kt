package com.manuellugodev.hotelmanagment.features.auth.domain

data class UserCredentials(

    val token: String,
    val guestId: Int,
)

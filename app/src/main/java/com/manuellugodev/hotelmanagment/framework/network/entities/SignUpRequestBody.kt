package com.manuellugodev.hotelmanagment.framework.network.entities

data class SignUpRequestBody(
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String
)
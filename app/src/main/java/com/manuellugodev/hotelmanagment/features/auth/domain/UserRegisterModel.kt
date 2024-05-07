package com.manuellugodev.hotelmanagment.features.auth.domain

data class UserRegisterModel(
    val userName:String,
    val password:String,
    val firstName:String,
    val lastName:String,
    val email:String,
    val phone:String
)
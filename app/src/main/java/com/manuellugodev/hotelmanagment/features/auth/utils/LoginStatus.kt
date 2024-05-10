package com.manuellugodev.hotelmanagment.features.auth.utils
data class LoginStatus(
    val loginSuccess: Boolean =false,
    val showLoader :Boolean =false,
    val showError :String = "",
    val usernameEnter:String = "",
    val passwordeEnter:String = "",
    val showPassword:Boolean =false
    )


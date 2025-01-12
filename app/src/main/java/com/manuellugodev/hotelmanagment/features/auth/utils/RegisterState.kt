package com.manuellugodev.hotelmanagment.features.auth.utils

data class RegisterState(

    val username:String="",
    val usernameError: String = "",
    val password:String ="",
    val confirmPassword: String = "",
    val passwordError: String = "",
    val firstName:String="",
    val lastName:String="",
    val email:String="",
    val emailError: String = "",
    val phone:String="",
    val isShowingPassword:Boolean=false,
    val msgError: String ="",
    val usernameAlreadyExist:Boolean=false,
    val emailAlreadyUsed:Boolean=false,
    val phoneNotValid:Boolean=false,
    val showLoader:Boolean=false,
    val navigateToLogin: Boolean = false,
)

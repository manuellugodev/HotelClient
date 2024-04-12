package com.manuellugodev.hotelmanagment.features.auth.utils

sealed class LoginStatus {
    class Success(val data: String) : LoginStatus()
    object Failure : LoginStatus()
    object Pending : LoginStatus()

    object Loading : LoginStatus()
}


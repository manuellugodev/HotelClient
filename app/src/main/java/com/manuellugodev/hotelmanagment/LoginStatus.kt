package com.manuellugodev.hotelmanagment

sealed class LoginStatus{
    class Success(val data:String) : LoginStatus()
    object Failure : LoginStatus()
    object Pending : LoginStatus()
}

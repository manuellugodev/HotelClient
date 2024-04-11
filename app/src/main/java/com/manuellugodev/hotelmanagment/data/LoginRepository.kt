package com.manuellugodev.hotelmanagment.data

import com.manuellugodev.hotelmanagment.LoginStatus

interface LoginRepository {

    suspend fun doLogin(email: String, password: String): LoginStatus

    suspend fun checkUserIsLogged(): LoginStatus
}
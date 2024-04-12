package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus


interface LoginRepository {

    suspend fun doLogin(email: String, password: String): LoginStatus

    suspend fun checkUserIsLogged(): LoginStatus
}
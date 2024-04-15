package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.utils.DataResult


interface LoginRepository {

    suspend fun doLogin(email: String, password: String): LoginStatus

    suspend fun doLogOut(): DataResult<Boolean>

    suspend fun checkUserIsLogged(): LoginStatus
}
package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.utils.DataResult


interface LoginRepository {

    suspend fun doLogin(email: String, password: String): Result<Unit>

    suspend fun doLogOut(): Result<Unit>

    suspend fun checkUserIsLogged(): Result<Unit>
}
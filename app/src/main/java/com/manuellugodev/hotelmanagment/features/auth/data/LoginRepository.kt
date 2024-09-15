package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel


interface LoginRepository {

    suspend fun doLogin(email: String, password: String): Result<Unit>

    suspend fun doLogOut(): Result<Unit>

    suspend fun checkUserIsLogged(): Result<Unit>

    suspend fun registerNewUser(userRegisterModel: UserRegisterModel): Result<Unit>
}
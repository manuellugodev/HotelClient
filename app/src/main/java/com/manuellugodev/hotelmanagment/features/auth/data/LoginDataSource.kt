package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.domain.UserCredentials
import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel

interface LoginDataSource {

    suspend fun loginWithEmailAndPassword(email: String, password: String): Result<UserCredentials>

    suspend fun registerNewUser(userRegisterModel: UserRegisterModel): Result<Unit>
}
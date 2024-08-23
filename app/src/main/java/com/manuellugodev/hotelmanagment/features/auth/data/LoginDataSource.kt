package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.domain.UserRegisterModel

interface LoginDataSource {

    suspend fun loginWithEmailAndPassword(email: String, password: String): Result<String>

    suspend fun registerNewUser(userRegisterModel: UserRegisterModel): Result<Unit>
}
package com.manuellugodev.hotelmanagment.features.auth.data

import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus

interface LoginDataSource {

    suspend fun loginWithEmailAndPassword(email: String, password: String): Result<String>
}
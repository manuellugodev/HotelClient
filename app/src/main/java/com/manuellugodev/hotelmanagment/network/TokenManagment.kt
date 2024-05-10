package com.manuellugodev.hotelmanagment.network

import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus

interface TokenManagment {
    suspend fun saveToken(token: String): Result<Unit>

    fun getToken(): String

    fun tokenIsAvailable(): Boolean
    fun removeToken(): Boolean
    fun getUsername(): String
}
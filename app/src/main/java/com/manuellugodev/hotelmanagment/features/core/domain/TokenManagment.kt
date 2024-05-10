package com.manuellugodev.hotelmanagment.features.core.domain

interface TokenManagment {
    suspend fun saveToken(token: String): Result<Unit>

    fun getToken(): String

    fun tokenIsAvailable(): Boolean
    fun removeToken(): Boolean
    fun getUsername(): String
}
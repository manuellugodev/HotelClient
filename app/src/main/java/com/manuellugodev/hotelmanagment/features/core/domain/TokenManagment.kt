package com.manuellugodev.hotelmanagment.features.core.domain

interface TokenManagment {
    suspend fun saveToken(token: String): Result<Unit>

    fun getToken(): String

    fun tokenIsAvailable(): Boolean
    fun removeToken(): Boolean
    suspend fun getUsername(): String
    fun saveUsernameAndGuestId(username: String, guestId: Int): Result<Unit>
    fun removeUsername(): Boolean
    fun usernameIsAvailable(): Boolean
    fun getGuestId(): Int
}
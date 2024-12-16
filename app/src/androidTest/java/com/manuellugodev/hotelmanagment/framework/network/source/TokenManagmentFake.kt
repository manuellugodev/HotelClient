package com.manuellugodev.hotelmanagment.framework.network.source

import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment

class TokenManagmentFake : TokenManagment {
    override suspend fun saveToken(token: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getToken(): String {
        TODO("Not yet implemented")
    }

    override fun tokenIsAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeToken(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun saveUsernameAndGuestId(username: String, guestId: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun removeUsername(): Boolean {
        TODO("Not yet implemented")
    }

    override fun usernameIsAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getGuestId(): Int {
        TODO("Not yet implemented")
    }
}
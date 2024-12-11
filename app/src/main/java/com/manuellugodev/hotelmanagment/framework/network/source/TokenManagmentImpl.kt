package com.manuellugodev.hotelmanagment.framework.network.source

import android.content.SharedPreferences
import com.manuellugodev.hotelmanagment.features.core.domain.TokenManagment

class TokenManagmentImpl(private val sharedPreferences: SharedPreferences) :
    TokenManagment {
    override suspend fun saveToken(token: String): Result<Unit> {
        try {
            sharedPreferences.edit().putString("token_key", token).apply()
            return Result.success(Unit)
        } catch (e: Exception) {

            return Result.failure(e)
        }

    }

    override fun getToken(): String {
        try {
            if (sharedPreferences.contains("token_key")) {

                val token = sharedPreferences.getString("token_key", "")

                if (!token.isNullOrEmpty()) {
                    return token
                }
            }

        } catch (e: Exception) {

            return ""

        }
        return ""
    }

    override fun tokenIsAvailable(): Boolean {
        return sharedPreferences.contains("token_key")
    }
    override fun usernameIsAvailable(): Boolean {
        return sharedPreferences.contains("username")
    }

    override fun removeToken(): Boolean {
        sharedPreferences.edit().remove("token_key").apply()

        return tokenIsAvailable().not()

    }

    override fun removeUsername():Boolean{
        sharedPreferences.edit().remove("username").apply()
        return usernameIsAvailable().not()
    }

    override suspend fun getUsername(): String {
        try {
            if (sharedPreferences.contains("username")) {
                val username = sharedPreferences.getString("username", "")
                if (!username.isNullOrEmpty()) {
                    return username
                }
            }
        } catch (e: Exception) {

        }

        return ""
    }

    override fun getGuestId(): Int {
        try {
            if (sharedPreferences.contains("guestId")) {
                val guestId = sharedPreferences.getInt("guestId", 0)
                if (guestId != 0) {
                    return guestId
                }
            }
        } catch (e: Exception) {

        }

        return 0
    }

    override fun saveUsernameAndGuestId(username: String, guestId: Int): Result<Unit> {
        try {
            sharedPreferences.edit().putString("username", username).apply()
            sharedPreferences.edit().putInt("guestId", guestId).apply()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
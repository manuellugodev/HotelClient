package com.manuellugodev.hotelmanagment.network.source

import android.content.SharedPreferences
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.network.TokenManagment

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

    override fun removeToken(): Boolean {
        sharedPreferences.edit().remove("token_key").apply()

        return tokenIsAvailable().not()

    }

    override fun getUsername(): String {
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
}
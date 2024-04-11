package com.manuellugodev.hotelmanagment.network.source

import android.content.SharedPreferences
import com.manuellugodev.hotelmanagment.LoginStatus
import com.manuellugodev.hotelmanagment.data.sources.TokenManagment

class TokenManagmentImpl(private val sharedPreferences: SharedPreferences) :
    TokenManagment {
    override suspend fun saveToken(token: String): LoginStatus {
        try {
            sharedPreferences.edit().putString("token_key", token).apply()
            return LoginStatus.Success("")
        } catch (e: Exception) {

            return LoginStatus.Failure
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
}
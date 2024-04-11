package com.manuellugodev.hotelmanagment.data.sources

import com.manuellugodev.hotelmanagment.LoginStatus

interface TokenManagment {
    suspend fun saveToken(token: String): LoginStatus

    fun getToken(): String
}
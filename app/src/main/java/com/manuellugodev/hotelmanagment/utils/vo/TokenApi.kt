package com.manuellugodev.hotelmanagment.utils.vo

import com.manuellugodev.hotelmanagment.network.TokenProvider

class TokenApi(private val token: String) : TokenProvider {

    override fun getToken(): String {
        return token
    }
}
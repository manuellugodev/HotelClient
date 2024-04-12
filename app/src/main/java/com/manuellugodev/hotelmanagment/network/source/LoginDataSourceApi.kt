package com.manuellugodev.hotelmanagment.network.source

import android.util.Log
import com.manuellugodev.hotelmanagment.features.auth.data.LoginDataSource
import com.manuellugodev.hotelmanagment.features.auth.utils.LoginStatus
import com.manuellugodev.hotelmanagment.network.entities.LoginRequestBody
import com.manuellugodev.hotelmanagment.network.request.LoginRequest

class LoginDataSourceApi(private val request: LoginRequest) : LoginDataSource {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): LoginStatus {
        try {

            val result = request.getService().doLogin(LoginRequestBody(email, password))
            if (result.isSuccessful) {
                return LoginStatus.Success(result.body()!!.token)
            } else {
                return LoginStatus.Failure
            }
        } catch (e: Exception) {
            Log.e("Login", "error ${e.message}")

            return LoginStatus.Failure
        }


    }
}